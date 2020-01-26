package com.sochanski;

import com.sochanski.container.Container;
import com.sochanski.container.ContainerRepository;
import com.sochanski.location.Location;
import com.sochanski.location.LocationRepository;
import com.sochanski.location.LocationType;
import com.sochanski.order.OrderHeaderRepository;
import com.sochanski.order.OrderLineRepository;
import com.sochanski.order.data.OrderHeader;
import com.sochanski.order.data.OrderHeaderStatus;
import com.sochanski.order.data.OrderLine;
import com.sochanski.pick.*;
import com.sochanski.sku.Sku;
import com.sochanski.sku.SkuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Collections.emptyList;

@Order(1)
@Component
public class TestDataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(TestDataLoader.class);

    private final OrderHeaderRepository orderHeaderRepository;
    private final OrderLineRepository orderLineRepository;
    private final SkuRepository skuRepository;
    private final LocationRepository locationRepository;
    private final ContainerRepository containerRepository;
    private final PickListRepository pickListRepository;
    private final PickTaskRepository pickTaskRepository;


    @Autowired
    public TestDataLoader(OrderHeaderRepository orderHeaderRepository,
                          OrderLineRepository orderLineRepository,
                          SkuRepository skuRepository,
                          LocationRepository locationRepository,
                          ContainerRepository containerRepository,
                          PickListRepository pickListRepository,
                          PickTaskRepository pickTaskRepository) {
        this.orderHeaderRepository = orderHeaderRepository;
        this.orderLineRepository = orderLineRepository;
        this.skuRepository = skuRepository;
        this.locationRepository = locationRepository;
        this.containerRepository = containerRepository;
        this.pickListRepository = pickListRepository;
        this.pickTaskRepository = pickTaskRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Location reg1241 = locationRepository.save(new Location("REG1241", LocationType.STORAGE, 15));
        Location reg1242 = locationRepository.save(new Location("REG1242", LocationType.STORAGE, 5));
        Location reg1244 = locationRepository.save(new Location("REG1244", LocationType.STORAGE, 15));
        Location inbound01 = locationRepository.save(new Location("INBOUND01", LocationType.INBOUND, 3));
        Location shipdock01 = locationRepository.save(new Location("SHIPDOCK01", LocationType.SHIPDOCK, 10));

        List<Sku> skuList = skuRepository.saveAll(List.of(
                new Sku("pantalony"),
                new Sku("krynolina"),
                new Sku("trykot"),
                new Sku("podwiązka"),
                new Sku("żupan"),
                new Sku("kaszkiet"),
                new Sku("skarpety"),
                new Sku("rękawiczki"),
                new Sku("pończochy")
        ));

        Container pantalonyReg1241 = containerRepository.save(new Container(reg1241, 5, skuList.get(0), 100, 100));

        Container zupanReg1241 = containerRepository.save(new Container(reg1241, 1, skuList.get(4), 100, 100));
        Container kaszkietReg1241 = containerRepository.save(new Container(reg1241, 1, skuList.get(5), 100, 100));
        Container zupanShipdock01 = containerRepository.save(new Container(shipdock01, 1, skuList.get(4), 0, 100));
        Container kaszkietShipdock01 = containerRepository.save(new Container(shipdock01, 1, skuList.get(5), 0, 100));

        Container krynolinaReg1242 = containerRepository.save(new Container(reg1242, 1, skuList.get(1), 50, 100));
        Container krynolinaShipdock01 = containerRepository.save(new Container(shipdock01, 1, skuList.get(1), 50, 100));

        OrderHeader orderHeader = orderHeaderRepository.save(new OrderHeader("order-1", OrderHeaderStatus.HOLD, emptyList()));
        orderLineRepository.saveAll(List.of(
                new OrderLine(1L, skuList.get(0), orderHeader),
                new OrderLine(2L, skuList.get(1), orderHeader),
                new OrderLine(3L, skuList.get(2), orderHeader)
        ));

        orderHeader = orderHeaderRepository.save(new OrderHeader("order-2", OrderHeaderStatus.RELEASED, emptyList()));
        OrderLine zupanOL = orderLineRepository.save(new OrderLine(1L, skuList.get(4), orderHeader));
        OrderLine kaszkietOL = orderLineRepository.save(new OrderLine(2L, skuList.get(5), orderHeader));

        PickList pickList = pickListRepository.save(new PickList(orderHeader));
        pickTaskRepository.save(new PickTask(pickList, zupanOL, PickTaskStatus.READY, zupanOL.getQty(), zupanReg1241, zupanShipdock01));
        pickTaskRepository.save(new PickTask(pickList, kaszkietOL, PickTaskStatus.READY, kaszkietOL.getQty(), kaszkietReg1241, kaszkietShipdock01));

        orderHeader = orderHeaderRepository.save(new OrderHeader("order-3", OrderHeaderStatus.COMPLETED, emptyList()));
        OrderLine krynolinaOL = orderLineRepository.save(new OrderLine(50L, skuList.get(1), orderHeader));

        pickList = pickListRepository.save(new PickList(orderHeader, PickListStatus.COMPLETED));
        pickTaskRepository.save(new PickTask(pickList, krynolinaOL, PickTaskStatus.COMPLETED, krynolinaOL.getQty(), krynolinaReg1242, krynolinaShipdock01));

        orderHeader = orderHeaderRepository.save(new OrderHeader("order-3", OrderHeaderStatus.SHIPPED, emptyList()));
        OrderLine ponczochyOL = orderLineRepository.save(new OrderLine(50L, skuList.get(8), orderHeader));

        pickList = pickListRepository.save(new PickList(orderHeader, PickListStatus.SHIPPED));
        pickTaskRepository.save(new PickTask(pickList, ponczochyOL, PickTaskStatus.SHIPPED, ponczochyOL.getQty()));

        printLocationCapacity(reg1241);
    }

    private void printLocationCapacity(Location reg1241) {
        long locationFreeCapacity = locationRepository.findLocationFreeCapacity(reg1241.getId()).get();
        long locationUsedCapacity = locationRepository.findLocationUsedCapacity(reg1241.getId()).get();
        log.info("Free capacity of {} location equal {}", reg1241.getName(), locationFreeCapacity);
        log.info("Used capacity of {} location equal {}", reg1241.getName(), locationUsedCapacity);
    }

}
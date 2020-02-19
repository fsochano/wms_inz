package com.sochanski;

import com.sochanski.container.Container;
import com.sochanski.container.ContainerRepository;
import com.sochanski.container.ContainerType;
import com.sochanski.location.Location;
import com.sochanski.location.LocationRepository;
import com.sochanski.location.LocationType;
import com.sochanski.order.OrderHeaderRepository;
import com.sochanski.order.OrderLineRepository;
import com.sochanski.order.data.OrderHeader;
import com.sochanski.order.data.OrderHeaderStatus;
import com.sochanski.order.data.OrderLine;
import com.sochanski.pick.*;
import com.sochanski.pick.data.PickList;
import com.sochanski.pick.data.PickListStatus;
import com.sochanski.pick.data.PickTask;
import com.sochanski.pick.data.PickTaskStatus;
import com.sochanski.security.SecurityUtils;
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

    private final ApplicationProperties applicationProperties;
    private final OrderHeaderRepository orderHeaderRepository;
    private final OrderLineRepository orderLineRepository;
    private final SkuRepository skuRepository;
    private final LocationRepository locationRepository;
    private final ContainerRepository containerRepository;
    private final PickListRepository pickListRepository;
    private final PickTaskRepository pickTaskRepository;


    @Autowired
    public TestDataLoader(ApplicationProperties applicationProperties,
                          OrderHeaderRepository orderHeaderRepository,
                          OrderLineRepository orderLineRepository,
                          SkuRepository skuRepository,
                          LocationRepository locationRepository,
                          ContainerRepository containerRepository,
                          PickListRepository pickListRepository,
                          PickTaskRepository pickTaskRepository) {
        this.applicationProperties = applicationProperties;
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
        if(!applicationProperties.isLoadTestData()){
            return;
        }
        log.info("Test data loading started");
        SecurityUtils.setSystemUser();
        
        Location reg1241 = createLocation("REG1241", LocationType.STORAGE, 15);
        Location reg1242 = createLocation("REG1242", LocationType.STORAGE, 5);
        Location reg1244 = createLocation("REG1244", LocationType.STORAGE, 15);
        Location inbound01 = createLocation("INBOUND01", LocationType.INBOUND, 3);
        Location shipdock01 = createLocation("SHIPDOCK01", LocationType.SHIPDOCK, 10);

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
        Sku pantalonySku = skuList.get(0);
        Sku zupanSku = skuList.get(4);
        Sku kaszkietSku = skuList.get(5);
        Sku krynolinaSku = skuList.get(1);
        Sku ponczochySku = skuList.get(8);

        Container pantalonyReg1241 = createContainer(ContainerType.STORAGE, reg1241, 5, pantalonySku, 100, 100);

        Container zupanReg1241 = createContainer(ContainerType.STORAGE, reg1241, 1, zupanSku, 100, 100);
        Container kaszkietReg1241 = createContainer(ContainerType.STORAGE, reg1241, 1, kaszkietSku, 100, 100);
        Container zupanShipdock01 = createContainer(ContainerType.SHIPPING, shipdock01, 1, zupanSku, 0, 100);
        Container kaszkietShipdock01 = createContainer(ContainerType.SHIPPING, shipdock01, 1, kaszkietSku, 0, 100);

        Container krynolinaReg1242 = createContainer(ContainerType.STORAGE, reg1242, 1, krynolinaSku, 50, 100);
        Container krynolinaShipdock01 = createContainer(ContainerType.SHIPPING, shipdock01, 1, krynolinaSku, 50, 100, 50);

        OrderHeader orderHeader = createOrderHeader("order-1", OrderHeaderStatus.HOLD);
        orderLineRepository.saveAll(List.of(
                new OrderLine(1L, pantalonySku, orderHeader),
                new OrderLine(2L, krynolinaSku, orderHeader),
                new OrderLine(3L, skuList.get(2), orderHeader)
        ));

        orderHeader = createOrderHeader("order-2", OrderHeaderStatus.RELEASED);
        OrderLine zupanOL = createOrderLine(zupanSku, orderHeader, 30L);
        OrderLine kaszkietOL = createOrderLine(kaszkietSku, orderHeader, 45L);

        PickList pickList = pickListRepository.save(new PickList(orderHeader));
        createPickTask(zupanReg1241, zupanShipdock01, zupanOL, pickList, PickTaskStatus.READY);
        createPickTask(kaszkietReg1241, kaszkietShipdock01, kaszkietOL, pickList, PickTaskStatus.READY);

        orderHeader = createOrderHeader("order-3", OrderHeaderStatus.COMPLETED);
        OrderLine krynolinaOL = createOrderLine(krynolinaSku, orderHeader, 50L);

        pickList = pickListRepository.save(new PickList(orderHeader, PickListStatus.COMPLETED));
        createPickTask(krynolinaReg1242, krynolinaShipdock01, krynolinaOL, pickList, PickTaskStatus.COMPLETED);

        orderHeader = createOrderHeader("order-3", OrderHeaderStatus.SHIPPED);
        OrderLine ponczochyOL = createOrderLine(ponczochySku, orderHeader, 50L);

        pickList = pickListRepository.save(new PickList(orderHeader, PickListStatus.SHIPPED));
        pickTaskRepository.save(new PickTask(pickList, ponczochyOL, ponczochyOL.getQty(), PickTaskStatus.SHIPPED));

        log.info("Test data loading ended");
        printLocationCapacity(reg1241);
    }

    private void createPickTask(Container from, Container to, OrderLine ol, PickList pickList, PickTaskStatus status) {
        pickTaskRepository.save(new PickTask(pickList, ol, status, ol.getQty(), from, to));
        if(PickTaskStatus.PICKED.getOrder() > status.getOrder()) {
            from.setAllocatedQty(ol.getQty());
            from.setFreeQty(from.getFreeQty() - ol.getQty());
            containerRepository.save(from);
        }
    }

    private OrderLine createOrderLine(Sku sku, OrderHeader orderHeader, long quantity) {
        return orderLineRepository.save(new OrderLine(quantity, sku, orderHeader));
    }

    private OrderHeader createOrderHeader(String name, OrderHeaderStatus status) {
        return orderHeaderRepository.save(new OrderHeader(name, status, emptyList()));
    }

    private Container createContainer(ContainerType type, Location location, int containerCapacity, Sku sku, int skuQuantity, int skuCapacity) {
        return containerRepository.save(new Container(type, location, containerCapacity, sku, skuQuantity, skuCapacity));
    }

    private Container createContainer(ContainerType type, Location location, int containerCapacity, Sku sku, int skuQuantity, int skuCapacity, int allocted) {
        Container container = new Container(type, location, containerCapacity, sku, skuQuantity, skuCapacity);
        container.setAllocatedQty(allocted);
        container.setFreeQty(skuQuantity - allocted);
        return containerRepository.save(container);
    }

    private Location createLocation(String name, LocationType type, int capacity) {
        return locationRepository.save(new Location(name, type, capacity));
    }

    private void printLocationCapacity(Location reg1241) {
        long locationFreeCapacity = locationRepository.findLocationFreeCapacity(reg1241.getId()).get();
        long locationUsedCapacity = locationRepository.findLocationUsedCapacity(reg1241.getId()).get();
        log.info("Free capacity of {} location equal {}", reg1241.getName(), locationFreeCapacity);
        log.info("Used capacity of {} location equal {}", reg1241.getName(), locationUsedCapacity);
    }

}
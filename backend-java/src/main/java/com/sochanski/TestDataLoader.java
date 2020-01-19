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


    @Autowired
    public TestDataLoader(OrderHeaderRepository orderHeaderRepository,
                          OrderLineRepository orderLineRepository,
                          SkuRepository skuRepository,
                          LocationRepository locationRepository,
                          ContainerRepository containerRepository) {
        this.orderHeaderRepository = orderHeaderRepository;
        this.orderLineRepository = orderLineRepository;
        this.skuRepository = skuRepository;
        this.locationRepository = locationRepository;
        this.containerRepository = containerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Location> locationList = locationRepository.saveAll(List.of(
                new Location("REG1241", LocationType.STORAGE, 15),
                new Location("REG1242", LocationType.STORAGE, 5),
                new Location("REG1244", LocationType.STORAGE, 15),
                new Location("INBOUND01", LocationType.INBOUND, 3),
                new Location("SHIPDOCK01", LocationType.SHIPDOCK, 2)
        ));

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

        List<Container> containerList = containerRepository.saveAll(List.of(
                new Container(locationList.get(0), 5, skuList.get(0), 100, 100)
        ));

        OrderHeader orderHeader = orderHeaderRepository.save(new OrderHeader("order-1", OrderHeaderStatus.HOLD, emptyList()));
        orderLineRepository.saveAll(List.of(
                new OrderLine(1L, skuList.get(0), orderHeader),
                new OrderLine(2L, skuList.get(1), orderHeader),
                new OrderLine(3L, skuList.get(2), orderHeader)
        ));

        orderHeader = orderHeaderRepository.save(new OrderHeader("order-2", OrderHeaderStatus.RELEASED, emptyList()));
        orderLineRepository.saveAll(List.of(
                new OrderLine(1L, skuList.get(4), orderHeader),
                new OrderLine(2L, skuList.get(5), orderHeader)
        ));


        orderHeader = orderHeaderRepository.save(new OrderHeader("order-3", OrderHeaderStatus.COMPLETED, emptyList()));
        orderLineRepository.saveAll(List.of(
                new OrderLine(1L, skuList.get(1), orderHeader)
        ));

        orderHeader = orderHeaderRepository.save(new OrderHeader("order-3", OrderHeaderStatus.SHIPPED, emptyList()));

        Location l = locationList.get(0);
        long locationFreeCapacity = locationRepository.findLocationFreeCapacity(l.id).get();
        long locationUsedCapacity = locationRepository.findLocationUsedCapacity(l.id).get();
        log.info("Free capacity of {} location equal {}", l.name, locationFreeCapacity);
        log.info("Used capacity of {} location equal {}", l.name, locationUsedCapacity);
    }

}
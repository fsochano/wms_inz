package com.sochanski;

import com.sochanski.container.Container;
import com.sochanski.container.ContainerRepository;
import com.sochanski.location.Location;
import com.sochanski.location.LocationRepository;
import com.sochanski.location.LocationType;
import com.sochanski.order.OrderLineRepository;
import com.sochanski.order.OrderRepository;
import com.sochanski.order.data.Order;
import com.sochanski.order.data.OrderLine;
import com.sochanski.order.data.OrderStatus;
import com.sochanski.sku.Sku;
import com.sochanski.sku.SkuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

import static java.util.Collections.emptyList;

@Component
public class DatabaseLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final SkuRepository skuRepository;
    private final LocationRepository locationRepository;
    private final ContainerRepository containerRepository;

    private final ResourceLoader resourceLoader;
    private final DataSource dataSource;

    @Autowired
    public DatabaseLoader(OrderRepository orderRepository,
                          OrderLineRepository orderLineRepository,
                          SkuRepository skuRepository,
                          LocationRepository locationRepository,
                          ContainerRepository containerRepository,
                          DataSource dataSource,
                          ResourceLoader resourceLoader) {
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.skuRepository = skuRepository;
        this.locationRepository = locationRepository;
        this.containerRepository = containerRepository;
        this.dataSource = dataSource;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(String... args) throws Exception {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user", "doesn't matter",
                        AuthorityUtils.createAuthorityList("ROLE_USER")));
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

        Order order = orderRepository.save(new Order("order-1", OrderStatus.HOLD, emptyList()));
        orderLineRepository.saveAll(List.of(
                new OrderLine(1L, skuList.get(0), order),
                new OrderLine(2L, skuList.get(1), order),
                new OrderLine(3L, skuList.get(2), order)
        ));

        order = orderRepository.save(new Order("order-2", OrderStatus.RELEASED, emptyList()));
        orderLineRepository.saveAll(List.of(
                new OrderLine(1L, skuList.get(4), order),
                new OrderLine(2L, skuList.get(5), order)
        ));


        order = orderRepository.save(new Order("order-3", OrderStatus.PICKED, emptyList()));
        orderLineRepository.saveAll(List.of(
                new OrderLine(1L, skuList.get(1), order)
        ));

        Location l = locationList.get(0);
        long locationFreeCapacity = locationRepository.findLocationFreeCapacity(l.id).get();
        long locationUsedCapacity = locationRepository.findLocationUsedCapacity(l.id).get();
        log.info("Free capacity of {} location equal {}", l.name, locationFreeCapacity);
        log.info("Used capacity of {} location equal {}", l.name, locationUsedCapacity);

        Resource[] sqls = ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
                .getResources("classpath:sql/*.sql");

        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.setSeparator("/\n");
        resourceDatabasePopulator.addScripts(sqls);
        resourceDatabasePopulator.execute(dataSource);
    }

}
package com.sochanski;

import com.sochanski.order.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseLoader implements CommandLineRunner {

	private final OrderRepository orderRepository;
	private final OrderLineRepository orderLineRepository;
	private final DataSource dataSource;

	@Value("classpath:sql/trigger.sql")
    Resource triggerSql;

	@Autowired
	public DatabaseLoader(OrderRepository orderRepository,
                          OrderLineRepository orderLineRepository,
                          DataSource dataSource) {
		this.orderRepository = orderRepository;
		this.orderLineRepository = orderLineRepository;
        this.dataSource = dataSource;
    }

	@Override
	public void run(String... args) throws Exception {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("user", "doesn't matter",
						AuthorityUtils.createAuthorityList("ROLE_USER")));

        List<OrderLine> lines = new ArrayList<>();
		lines.add(orderLineRepository.save(new OrderLine(1L, "pantalony")));
        lines.add(orderLineRepository.save(new OrderLine(2L, "krynolina")));
        lines.add(orderLineRepository.save(new OrderLine(3L, "trykot")));
        orderRepository.save(new Order("order-1", OrderStatus.HOLD, lines));

        lines = new ArrayList<>();
        lines.add(orderLineRepository.save(new OrderLine(1L, "podwiązka")));
        lines.add(orderLineRepository.save(new OrderLine(2L, "żupan")));
        lines.add(orderLineRepository.save(new OrderLine(3L, "kaszkiet")));
        orderRepository.save(new Order("order-2", OrderStatus.RELEASED, lines));

        lines = new ArrayList<>();
        lines.add(orderLineRepository.save(new OrderLine(1L, "skarpety")));
        lines.add(orderLineRepository.save(new OrderLine(2L, "rękawiczki")));
        lines.add(orderLineRepository.save(new OrderLine(3L, "pończochy")));
        orderRepository.save(new Order("order-3",  OrderStatus.PICKED, lines));

        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.setSeparator("/\n");
        resourceDatabasePopulator.addScript(triggerSql);
        resourceDatabasePopulator.execute(dataSource);
	}

}
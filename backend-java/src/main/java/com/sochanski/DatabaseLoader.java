/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sochanski;

import com.sochanski.order.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseLoader implements CommandLineRunner {

	private final OrderRepository orderRepository;
	private final OrderLineRepository orderLineRepository;

	@Autowired
	public DatabaseLoader(OrderRepository orderRepository, OrderLineRepository orderLineRepository) {
		this.orderRepository = orderRepository;
		this.orderLineRepository = orderLineRepository;
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

	}

}
package com.sochanski.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/api")
@CrossOrigin
public class OrdersController {

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;

    @Autowired
    OrdersController(OrderRepository orderRepository, OrderLineRepository orderLineRepository) {
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
    }

    @GetMapping(path = "orders")
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @GetMapping(path = "orders/{id}")
    public Optional<Order> getOrders(@PathVariable long id) {
        return orderRepository.findById(id);
    }

    @PostMapping(path = "orders/{id}")
    public Optional<Order> updateOrder(@PathVariable long id, @RequestBody OrderParameters orderParameters) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.name = orderParameters.name;
                    return order;
                }).map(orderRepository::save);
    }

    @DeleteMapping(path = "orders/{id}")
    public void deleteOrder(@PathVariable long id) {
        orderRepository.deleteById(id);
    }

    @PostMapping(path = "orders")
    public Order createOrders(@RequestBody OrderParameters orderParameters) {
        return orderRepository.save(new Order(orderParameters.name, new ArrayList<>()));
    }

    @GetMapping(path = "orders/{id}/orderlines")
    public List<OrderLine> getOrderLines(@PathVariable long id) {
        return orderRepository.findById(id).map(o -> o.orderLines).orElse(new ArrayList<>());
    }

    @PostMapping(path = "orders/{id}/orderlines")
    public OrderLine createOrderLine(@PathVariable long id, @RequestBody OrderLineParameters orderLineParameters) {
        Optional<Order> oo = orderRepository.findById(id);
        if(oo.isPresent()) {
            Order order = oo.get();
            OrderLine orderLine = new OrderLine(orderLineParameters.qty, orderLineParameters.item);
            orderLine = orderLineRepository.save(orderLine);
            order.orderLines.add(orderLine);
            orderRepository.save(order);
            return orderLine;
        }
        throw new RuntimeException("No order found");
    }
}

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

    @PostMapping(path = "orders")
    public Order createOrders(@RequestBody OrderCreateParams orderCreateParams) {
        return orderRepository.save(new Order(orderCreateParams.name));
    }

    @GetMapping(path = "orders/{id}")
    public Optional<Order> getOrders(@PathVariable long id) {
        return orderRepository.findById(id);
    }

    @PostMapping(path = "orders/{id}")
    public Optional<Order> updateOrder(@PathVariable long id, @RequestBody OrderUpdateParams orderUpdateParams) {
        return orderRepository.findById(id)
                .map(order -> {
                    orderUpdateParams.name.ifPresent(a -> order.name = a);
                    orderUpdateParams.status.ifPresent(a -> order.status = a);
                    return order;
                }).map(orderRepository::save);
    }

    @DeleteMapping(path = "orders/{id}")
    public void deleteOrder(@PathVariable long id) {
        orderRepository.deleteById(id);
    }

    @GetMapping(path = "orders/{orderId}/orderlines")
    public List<OrderLine> getOrderLines(@PathVariable long orderId) {
        return orderRepository.findById(orderId).map(o -> o.orderLines).orElse(new ArrayList<>());
    }

    @PostMapping(path = "orders/{orderId}/orderlines")
    public OrderLine createOrderLine(@PathVariable long orderId, @RequestBody OrderLineParameters orderLineParameters) {
        Optional<Order> oo = orderRepository.findById(orderId);
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

    @DeleteMapping(path = "orders/{orderId}/orderlines/{orderLineId}")
    public void deleteOrderLine(@PathVariable long orderId, @PathVariable long orderLineId ) {
        Optional<Order> oo = orderRepository.findById(orderId);
        if(oo.isPresent()) {
            Order order = oo.get();
            order.orderLines.removeIf(orderLine -> orderLine.id == orderLineId);
            orderRepository.save(order);
            orderLineRepository.deleteById(orderLineId);
        }
    }
}

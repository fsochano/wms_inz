package com.sochanski.order;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        if (orderCreateParams.name == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong request body");
        }
        return orderRepository.save(new Order(orderCreateParams.name));
    }

    @GetMapping(path = "orders/{id}")
    public Order getOrders(@PathVariable long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "orders/{id}")
    public Order updateOrder(@PathVariable long id, @RequestBody OrderUpdateParams orderUpdateParams) {
        return orderRepository.findById(id)
                .map(order -> {
//                    if(order.status != OrderStatus.HOLD) {
//                        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
//                    }
                    orderUpdateParams.name.ifPresent(a -> order.name = a);
                    orderUpdateParams.status.ifPresent(a -> order.status = a);
                    return order;
                }).map(orderRepository::save)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "orders/{id}")
    public void deleteOrder(@PathVariable long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(order.status != OrderStatus.HOLD) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        orderRepository.deleteById(id);
    }

    @GetMapping(path = "orders/{orderId}/order-lines")
    public List<OrderLine> getOrderLines(@PathVariable long orderId) {
        return orderRepository.findById(orderId)
                .map(o -> o.orderLines)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "orders/{orderId}/order-lines")
    public OrderLine createOrderLine(@PathVariable long orderId, @RequestBody OrderLineParameters orderLineParameters) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(order.status != OrderStatus.HOLD) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (orderLineParameters.qty <= 0 || Strings.isNullOrEmpty(orderLineParameters.item)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong request body");
        }
        OrderLine orderLine = new OrderLine(orderLineParameters.qty, orderLineParameters.item);
        orderLine = orderLineRepository.save(orderLine);
        order.orderLines.add(orderLine);
        orderRepository.save(order);
        return orderLine;
    }

    @DeleteMapping(path = "orders/{orderId}/order-lines/{orderLineId}")
    public void deleteOrderLine(@PathVariable long orderId, @PathVariable long orderLineId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(order.status != OrderStatus.HOLD) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        if(order.orderLines.removeIf(orderLine -> orderLine.id == orderLineId)) {
            orderRepository.save(order);
            orderLineRepository.deleteById(orderLineId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}

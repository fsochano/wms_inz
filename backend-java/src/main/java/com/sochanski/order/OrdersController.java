package com.sochanski.order;

import com.sochanski.ApiUtils;
import com.sochanski.order.data.Order;
import com.sochanski.order.data.OrderCreateParams;
import com.sochanski.order.data.OrderUpdateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(path = ApiUtils.BASE_API_PATH + "/orders")
@CrossOrigin
public class OrdersController {

    private final OrderService service;

    @Autowired
    OrdersController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<Order> getOrders() {
        return service.getAllOrders();
    }

    @PostMapping
    public Order createOrders(@RequestBody @Valid OrderCreateParams orderCreateParams) {
        return service.createOrder(orderCreateParams);
    }

    @GetMapping(path = "/{orderId}")
    public Order getOrder(@PathVariable long orderId) {
        return service.getOrder(orderId);
    }

    @PostMapping(path = "/{orderId}/release")
    public Order releaseOrder(@PathVariable long orderId) {
        return service.releaseOrder(orderId);
    }

    @PostMapping(path = "/{orderId}")
    public Order updateOrder(@PathVariable long orderId, @RequestBody @Valid OrderUpdateParams orderUpdateParams) {
        return service.updateOrder(orderId, orderUpdateParams);
    }

    @DeleteMapping(path = "/{orderId}")
    public void deleteOrder(@PathVariable long orderId) {
        service.deleteOrder(orderId);
    }
}

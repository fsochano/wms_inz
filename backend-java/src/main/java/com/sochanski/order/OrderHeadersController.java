package com.sochanski.order;

import com.sochanski.ApiUtils;
import com.sochanski.order.data.OrderHeader;
import com.sochanski.order.data.OrderHeaderCreateParams;
import com.sochanski.order.data.OrderHeaderUpdateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@PreAuthorize(value ="hasAuthority('ORDERING')")
@RestController
@RequestMapping(path = ApiUtils.BASE_API_PATH + "/orders")
@CrossOrigin
public class OrderHeadersController {

    private final OrderHeaderService service;

    @Autowired
    OrderHeadersController(OrderHeaderService service) {
        this.service = service;
    }

    @GetMapping
    public List<OrderHeader> getOrders() {
        return service.getAllOrders();
    }

    @PostMapping
    public OrderHeader createOrders(@RequestBody @Valid OrderHeaderCreateParams orderHeaderCreateParams) {
        return service.createOrder(orderHeaderCreateParams);
    }

    @GetMapping(path = "/{orderId}")
    public OrderHeader getOrder(@PathVariable long orderId) {
        return service.getOrder(orderId);
    }

    @PostMapping(path = "/{orderId}/release")
    public OrderHeader releaseOrder(@PathVariable long orderId) {
        return service.releaseOrder(orderId);
    }

    @PostMapping(path = "/{orderId}")
    public OrderHeader updateOrder(@PathVariable long orderId, @RequestBody @Valid OrderHeaderUpdateParams orderHeaderUpdateParams) {
        return service.updateOrder(orderId, orderHeaderUpdateParams);
    }

    @DeleteMapping(path = "/{orderId}")
    public void deleteOrder(@PathVariable long orderId) {
        service.deleteOrder(orderId);
    }
}

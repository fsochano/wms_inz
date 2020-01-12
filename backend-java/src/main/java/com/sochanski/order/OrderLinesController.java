package com.sochanski.order;

import com.sochanski.ApiUtils;
import com.sochanski.order.data.OrderLine;
import com.sochanski.order.data.OrderLineParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = ApiUtils.BASE_API_PATH + "/orders/{orderId}/order-lines")
@CrossOrigin
public class OrderLinesController {

    private final OrderLinesService service;

    @Autowired
    public OrderLinesController(OrderLinesService service) {
        this.service = service;
    }

    @GetMapping
    public List<OrderLine> getOrderLines(@PathVariable long orderId) {
        return service.getOrderLinesForOrder(orderId);
    }

    @PostMapping
    public OrderLine createOrderLine(@PathVariable long orderId, @RequestBody @Valid OrderLineParameters orderLineParameters) {
        return service.createOrderLineForOrder(orderId, orderLineParameters);
    }

    @DeleteMapping(path = "/{orderLineId}")
    public void deleteOrderLine(@PathVariable long orderId, @PathVariable long orderLineId) {
        service.deleteOrderLine(orderId, orderLineId);
    }
}

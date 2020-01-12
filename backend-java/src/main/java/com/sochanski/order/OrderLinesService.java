package com.sochanski.order;

import com.sochanski.order.data.Order;
import com.sochanski.order.data.OrderLine;
import com.sochanski.order.data.OrderLineParameters;
import com.sochanski.sku.Sku;
import com.sochanski.sku.SkuNotFoundException;
import com.sochanski.sku.SkuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLinesService {

    private final OrderService orderService;
    private final OrderLineRepository orderLineRepository;
    private final SkuRepository skuRepository;

    public OrderLinesService(OrderService orderService, OrderLineRepository orderLineRepository, SkuRepository skuRepository) {
        this.orderService = orderService;
        this.orderLineRepository = orderLineRepository;
        this.skuRepository = skuRepository;
    }

    public List<OrderLine> getOrderLinesForOrder(long orderId) {
        return orderService.getOrder(orderId).orderLines;
    }

    public OrderLine createOrderLineForOrder(long orderId, OrderLineParameters orderLineParameters) {
        Order order = orderService.getOrder(orderId);
        orderService.checkOrderStatusHold(order);

        if (orderLineParameters.qty <= 0) {
            throw new BadOrderLinesParametersException();
        }
        Sku sku = skuRepository.findById(orderLineParameters.skuId).orElseThrow(SkuNotFoundException::new);
        OrderLine orderLine = new OrderLine(orderLineParameters.qty, sku, order);
        return orderLineRepository.save(orderLine);
    }

    public void deleteOrderLine(long orderId, long orderLineId) {
        Order order = orderService.getOrder(orderId);
        orderService.checkOrderStatusHold(order);

        if(!orderLineRepository.existsById(orderLineId)) {
            throw new OrderLineNotFoundException();
        }
        orderLineRepository.deleteById(orderLineId);
    }

}

package com.sochanski.order;

import com.sochanski.order.data.OrderHeader;
import com.sochanski.order.data.OrderLine;
import com.sochanski.order.data.OrderLineParameters;
import com.sochanski.sku.Sku;
import com.sochanski.sku.SkuNotFoundException;
import com.sochanski.sku.SkuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLinesService {

    private final OrderHeaderService orderHeaderService;
    private final OrderLineRepository orderLineRepository;
    private final SkuRepository skuRepository;

    public OrderLinesService(OrderHeaderService orderHeaderService, OrderLineRepository orderLineRepository, SkuRepository skuRepository) {
        this.orderHeaderService = orderHeaderService;
        this.orderLineRepository = orderLineRepository;
        this.skuRepository = skuRepository;
    }

    public List<OrderLine> getOrderLinesForOrder(long orderId) {
        return orderHeaderService.getOrder(orderId).orderLines;
    }

    public OrderLine createOrderLineForOrder(long orderId, OrderLineParameters orderLineParameters) {
        OrderHeader orderHeader = orderHeaderService.getOrder(orderId);
        orderHeaderService.checkOrderStatusHold(orderHeader);

        if (orderLineParameters.qty <= 0) {
            throw new BadOrderLinesParametersException();
        }
        Sku sku = skuRepository.findById(orderLineParameters.skuId).orElseThrow(SkuNotFoundException::new);
        OrderLine orderLine = new OrderLine(orderLineParameters.qty, sku, orderHeader);
        return orderLineRepository.save(orderLine);
    }

    public void deleteOrderLine(long orderId, long orderLineId) {
        OrderHeader orderHeader = orderHeaderService.getOrder(orderId);
        orderHeaderService.checkOrderStatusHold(orderHeader);

        if(!orderLineRepository.existsById(orderLineId)) {
            throw new OrderLineNotFoundException();
        }
        orderLineRepository.deleteById(orderLineId);
    }

}

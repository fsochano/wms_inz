package com.sochanski.order;

import com.sochanski.ApplicationProperties;
import com.sochanski.order.data.OrderHeader;
import com.sochanski.order.data.OrderHeaderCreateParams;
import com.sochanski.order.data.OrderHeaderStatus;
import com.sochanski.order.data.OrderHeaderUpdateParams;
import com.sochanski.processing.picks.PicksGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderHeaderService {

    private final OrderHeaderRepository orderHeaderRepository;
    private final ApplicationProperties applicationProperties;
    private final PicksGenerationService picksGenerationService;

    @Autowired
    public OrderHeaderService(OrderHeaderRepository orderHeaderRepository, ApplicationProperties applicationProperties, PicksGenerationService picksGenerationService) {
        this.applicationProperties = applicationProperties;
        this.orderHeaderRepository = orderHeaderRepository;
        this.picksGenerationService = picksGenerationService;
    }

    public List<OrderHeader> getAllOrders() {
        return orderHeaderRepository.findAll();
    }

    public OrderHeader createOrder(OrderHeaderCreateParams orderHeaderCreateParams) {
        if (orderHeaderCreateParams.name == null) {
            throw new BadOrderHeaderParametersException();
        }
        return orderHeaderRepository.save(new OrderHeader(orderHeaderCreateParams.name));
    }

    public OrderHeader getOrder(long orderId) {
        return orderHeaderRepository.findById(orderId).orElseThrow(OrderHeaderNotFoundException::new);
    }

    public OrderHeader releaseOrder(long id) {
        var header =  orderHeaderRepository.findById(id)
                .map(order -> {
                    checkOrderStatusHold(order);
                    order.setStatus(OrderHeaderStatus.RELEASED);
                    return order;
                }).map(orderHeaderRepository::save)
                .orElseThrow(OrderHeaderNotFoundException::new);
        if(applicationProperties.isJavaProcessing()) {
            picksGenerationService.schedulePicksGeneration(header.getId());
        }
        return header;
    }

    public OrderHeader updateOrder(long id, OrderHeaderUpdateParams params) {
        return orderHeaderRepository.findById(id)
                .map(order -> {
                    params.name.ifPresent(order::setName);
                    return order;
                }).map(orderHeaderRepository::save)
                .orElseThrow(OrderHeaderNotFoundException::new);
    }

    public void deleteOrder(long id) {
        OrderHeader orderHeader = orderHeaderRepository.findById(id)
                .orElseThrow(OrderHeaderNotFoundException::new);
        checkOrderStatusHold(orderHeader);
        orderHeaderRepository.deleteById(id);
    }

    public void checkOrderStatusHold(OrderHeader orderHeader) {
        if (orderHeader.getStatus() != OrderHeaderStatus.HOLD) {
            throw new WrongOrderHeaderStatusException(OrderHeaderStatus.HOLD);
        }
    }

}

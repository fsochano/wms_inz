package com.sochanski.order;

import com.sochanski.order.data.OrderHeader;
import com.sochanski.order.data.OrderHeaderCreateParams;
import com.sochanski.order.data.OrderHeaderStatus;
import com.sochanski.order.data.OrderHeaderUpdateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderHeaderService {

    private final OrderHeaderRepository orderHeaderRepository;

    @Autowired
    public OrderHeaderService(OrderHeaderRepository orderHeaderRepository) {
        this.orderHeaderRepository = orderHeaderRepository;
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
        return orderHeaderRepository.findById(id)
                .map(order -> {
                    checkOrderStatusHold(order);
                    order.setStatus(OrderHeaderStatus.RELEASED);
                    return order;
                }).map(orderHeaderRepository::save)
                .orElseThrow(OrderHeaderNotFoundException::new);
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

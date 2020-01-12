package com.sochanski.order;

import com.sochanski.order.data.Order;
import com.sochanski.order.data.OrderCreateParams;
import com.sochanski.order.data.OrderStatus;
import com.sochanski.order.data.OrderUpdateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(OrderCreateParams orderCreateParams) {
        if (orderCreateParams.name == null) {
            throw new BadOrderParametersException();
        }
        return orderRepository.save(new Order(orderCreateParams.name));
    }

    public Order getOrder(long orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    public Order releaseOrder(long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    checkOrderStatusHold(order);
                    order.status = OrderStatus.RELEASED;
                    return order;
                }).map(orderRepository::save)
                .orElseThrow(OrderNotFoundException::new);
    }

    public Order updateOrder(long id, OrderUpdateParams params) {
        return orderRepository.findById(id)
                .map(order -> {
                    params.name.ifPresent(a -> order.name = a);
                    return order;
                }).map(orderRepository::save)
                .orElseThrow(OrderNotFoundException::new);
    }

    public void deleteOrder(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);
        checkOrderStatusHold(order);
        orderRepository.deleteById(id);
    }

    public void checkOrderStatusHold(Order order) {
        if (order.status != OrderStatus.HOLD) {
            throw new WrongOrderStatusException(OrderStatus.HOLD);
        }
    }

}

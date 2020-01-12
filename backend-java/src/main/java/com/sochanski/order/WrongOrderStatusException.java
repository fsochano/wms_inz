package com.sochanski.order;

import com.sochanski.order.data.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WrongOrderStatusException extends ResponseStatusException {
    public WrongOrderStatusException(OrderStatus status) {
        super(HttpStatus.FORBIDDEN, "Cannot do operation for order is " + status + " status");
    }
}

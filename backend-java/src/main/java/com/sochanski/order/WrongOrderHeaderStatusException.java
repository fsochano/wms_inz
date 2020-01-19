package com.sochanski.order;

import com.sochanski.order.data.OrderHeaderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WrongOrderHeaderStatusException extends ResponseStatusException {
    public WrongOrderHeaderStatusException(OrderHeaderStatus status) {
        super(HttpStatus.FORBIDDEN, "Cannot do operation for order is not in " + status + " status");
    }
}

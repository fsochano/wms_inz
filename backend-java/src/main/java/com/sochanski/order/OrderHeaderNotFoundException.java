package com.sochanski.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderHeaderNotFoundException extends ResponseStatusException {
    public OrderHeaderNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Order not found");
    }
}

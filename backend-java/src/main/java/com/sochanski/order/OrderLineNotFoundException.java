package com.sochanski.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderLineNotFoundException extends ResponseStatusException {
    public OrderLineNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Order line not found");
    }
}

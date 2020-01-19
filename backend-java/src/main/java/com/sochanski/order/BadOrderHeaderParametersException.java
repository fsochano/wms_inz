package com.sochanski.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadOrderHeaderParametersException extends ResponseStatusException {
    public BadOrderHeaderParametersException() {
        super(HttpStatus.BAD_REQUEST, "Wrong request body");
    }
}

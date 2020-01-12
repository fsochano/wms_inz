package com.sochanski.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadOrderLinesParametersException extends ResponseStatusException {
    public BadOrderLinesParametersException() {
        super(HttpStatus.BAD_REQUEST, "Wrong request body");
    }
}

package com.sochanski.sku;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadSkuParametersException extends ResponseStatusException {
    public BadSkuParametersException() {
        super(HttpStatus.BAD_REQUEST, "Wrong request body");
    }
}

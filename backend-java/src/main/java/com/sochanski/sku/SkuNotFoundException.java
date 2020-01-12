package com.sochanski.sku;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SkuNotFoundException extends ResponseStatusException {
    public SkuNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Sku not found");
    }
}

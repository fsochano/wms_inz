package com.sochanski.sku;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SkuDeleteException  extends ResponseStatusException {
    public SkuDeleteException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}

package com.sochanski.pick;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotEnoughSpaceInContainerException extends ResponseStatusException {
    public NotEnoughSpaceInContainerException() {
        super(HttpStatus.FORBIDDEN, "Not enough space in container");
    }
}
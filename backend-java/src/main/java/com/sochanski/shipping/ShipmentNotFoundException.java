package com.sochanski.shipping;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ShipmentNotFoundException extends ResponseStatusException {
    public ShipmentNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Shipment not found");
    }
}

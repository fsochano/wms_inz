package com.sochanski.shipping;

import com.sochanski.ApiUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize(value ="hasAuthority('SHIPPING')")
@RestController
@RequestMapping(path = ApiUtils.BASE_API_PATH + "/shipments")
@CrossOrigin
public class ShipmentController {

    private final ShippingService service;

    public ShipmentController(ShippingService service) {
        this.service = service;
    }

    @GetMapping
    public List<Shipment> getShipmentList() {
        return service.getAllShipments();
    }

    @PostMapping(path = "/{shipmentId}/ship")
    public Shipment completeShipment(@PathVariable long shipmentId) {
        return service.shipShipment(shipmentId);

    }
}
package com.sochanski.shipping;

import com.sochanski.pick.PickListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShippingService {

    private final ShippingRepository shipmentRepository;
    private final PickListService pickListService;


    @Autowired
    public ShippingService(ShippingRepository shipmentRepository, PickListService pickListService) {
        this.shipmentRepository = shipmentRepository;
        this.pickListService = pickListService;
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public Shipment getShipment(long shipmentId) {
        return shipmentRepository.findById(shipmentId).orElseThrow(ShipmentNotFoundException::new); // TODO exeption
    }
    
    @Transactional
    public Shipment shipShipment(long shipmentId) {
        return  shipmentRepository.findById(shipmentId)
                .map(shipment -> {
                    shipment.setStatus(ShippingStatus.SHIPPED);
                    return shipment;
                }).map(shipmentRepository::save)
                .map(shipment -> {
                    pickListService.ship(shipment.getPickListId());
                    return shipment;
                })
                .orElseThrow(ShipmentNotFoundException::new);
    }

}
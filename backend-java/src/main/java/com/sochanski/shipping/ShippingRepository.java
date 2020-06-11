package com.sochanski.shipping;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<Shipment, Long> {
}
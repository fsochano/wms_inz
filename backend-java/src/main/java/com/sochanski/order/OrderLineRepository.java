package com.sochanski.order;

import com.sochanski.order.data.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    boolean existsBySkuId(long skuId);
}

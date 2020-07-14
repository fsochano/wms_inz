package com.sochanski.container;

import com.sochanski.sku.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContainerRepository extends JpaRepository<Container, Long> {
    @Query("select c from Container c where c.sku = ?1 and c.freeQty > 0  and c.type ='STORAGE' order by c.freeQty asc")
    List<Container> findBySkuAndFreeQtyGreaterThan0AndTypeEqualStorageOrderByFreeQtyAsc(Sku sku);
}

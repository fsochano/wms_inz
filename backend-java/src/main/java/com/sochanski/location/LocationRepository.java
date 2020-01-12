package com.sochanski.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("select l.capacity - sum(c.containerSize) from Container c join c.location l where l.id = ?1 group by l.capacity")
    Optional<Long> findLocationFreeCapacity(long locationId);
    @Query("select sum(c.containerSize) from Container c where c.location.id = ?1")
    Optional<Long> findLocationUsedCapacity(long locationId);
}

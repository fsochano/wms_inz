package com.sochanski.container;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sochanski.db.Auditable;
import com.sochanski.location.Location;
import com.sochanski.sku.Sku;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@Entity
@Table(name = "container")
public class Container extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "container_id_gen")
    @SequenceGenerator(name = "container_id_gen", sequenceName = "container_id_gen", allocationSize = 1)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private ContainerType type;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Positive
    private long containerSize;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "sku_id", nullable = false)
    private Sku sku;
    @Positive
    private long skuCapacity;

    @PositiveOrZero
    private long skuQty;
    @PositiveOrZero
    private long allocatedQty;
    @PositiveOrZero
    private long freeQty;

    public Container(ContainerType type, Location location, int containerSize, Sku sku, int skuQty, int skuCapacity){
        this.type = type;
        this.location = location;
        this.containerSize = containerSize;
        this.sku = sku;
        this.skuQty = skuQty;
        this.freeQty = skuQty;
        this.skuCapacity = skuCapacity;
    }

}

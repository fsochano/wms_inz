package com.sochanski.container;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Container {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "container_id_gen")
    @SequenceGenerator(name = "container_id_gen", sequenceName = "container_id_gen", allocationSize = 1)
    private long id;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Positive
    private long containerSize;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "sku_id", nullable = false)
    private Sku sku;

    @PositiveOrZero
    private long skuQty;
    @Positive
    private long skuCapacity;

    public Container(Location location, int containerSize, Sku sku, int skuQty, int skuCapacity){
        this.location = location;
        this.containerSize = containerSize;
        this.sku = sku;
        this.skuQty = skuQty;
        this.skuCapacity = skuCapacity;
    }

}

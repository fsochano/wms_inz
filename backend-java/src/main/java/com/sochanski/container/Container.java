package com.sochanski.container;

import com.sochanski.location.Location;
import com.sochanski.sku.Sku;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "container")
public class Container {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "container_id_gen")
    @SequenceGenerator(name = "container_id_gen", sequenceName = "container_id_gen", allocationSize = 1)
    public long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    @LazyToOne(value = LazyToOneOption.PROXY)
    public Location location;

    @Positive
    public long containerSize;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sku_id", nullable = false)
    @LazyToOne(value = LazyToOneOption.PROXY)
    public Sku sku;

    @PositiveOrZero
    public long skuQty;
    @Positive
    public long skuCapacity;

    public Container(){
    }

    public Container(Location location, int containerSize, Sku sku, int skuQty, int skuCapacity){
        this.location = location;
        this.containerSize = containerSize;
        this.sku = sku;
        this.skuQty = skuQty;
        this.skuCapacity = skuCapacity;
    }

}

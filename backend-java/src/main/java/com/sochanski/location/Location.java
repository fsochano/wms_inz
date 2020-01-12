package com.sochanski.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sochanski.container.Container;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_id_gen")
    @SequenceGenerator(name = "location_id_gen", sequenceName = "location_id_gen", allocationSize = 1)
    public long id;

    @JsonIgnore
    @OneToMany(mappedBy = "location")
    @LazyCollection(value = LazyCollectionOption.TRUE)
    public List<Container> containers;

    @NotEmpty
    public String name;

    @MapKeyEnumerated(EnumType.STRING)
    public LocationType locationType;

    @Positive
    public long capacity;

    @PositiveOrZero
    @Formula("coalesce((select sum(c.container_size) from Container c where c.location_id = id), 0)")
    public long usedCapacity;

    @PositiveOrZero
    @Formula("capacity - coalesce((select sum(c.container_size) from Container c where c.location_id = id), 0)")
    public long freeCapacity;

    public Location() {
    }

    public Location(String name, LocationType locationType, long capacity) {
        this.name = name;
        this.locationType = locationType;
        this.capacity = capacity;
        this.freeCapacity = capacity;
    }

    public Location(LocationParameters params) {
        this.name = params.name;
        this.locationType = params.locationType;
        this.capacity = params.capacity;
        this.freeCapacity = params.capacity;
    }
}

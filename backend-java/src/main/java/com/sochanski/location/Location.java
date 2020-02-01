package com.sochanski.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sochanski.container.Container;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static java.util.Collections.emptyList;

@Data
@NoArgsConstructor
@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_id_gen")
    @SequenceGenerator(name = "location_id_gen", sequenceName = "location_id_gen", allocationSize = 1)
    private long id;

    @JsonIgnore
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<Container> containers = emptyList();

    @NotEmpty
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private LocationType locationType;

    @Positive
    private long capacity;

    @PositiveOrZero
    @Formula("coalesce((select sum(c.container_size) from Container c where c.location_id = id), 0)")
    private long usedCapacity;

    @PositiveOrZero
    @Formula("capacity - coalesce((select sum(c.container_size) from Container c where c.location_id = id), 0)")
    private long freeCapacity;

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

package com.sochanski.order.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_header")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="order_header_id_gen")
    @SequenceGenerator(name="order_header_id_gen", sequenceName="order_header_id_gen", allocationSize=1)
    public long id;

    @NotNull
    @NotEmpty
    public String name;

    @NotNull
    @MapKeyEnumerated(EnumType.STRING)
    public OrderStatus status;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    @LazyCollection(value = LazyCollectionOption.TRUE)
    public List<OrderLine> orderLines;

    public Order(String name) {
        this(name, OrderStatus.HOLD, new ArrayList<>());
    }

    public Order(String name, OrderStatus status, List<OrderLine> orderLines) {
        this.name = name;
        this.status = status;
        this.orderLines = orderLines;
    }

    public Order(){
    }
}
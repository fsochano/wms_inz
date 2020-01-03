package com.sochanski.order;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_header")
public class Order {

    @Id @GeneratedValue
    public long id;
    public String name;
    public OrderStatus status;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE)
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
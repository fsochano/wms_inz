package com.sochanski.order;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "order_header")
public class Order {

    @Id @GeneratedValue
    public long id;
    public String name;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE)
    public List<OrderLine> orderLines;

    public Order(String name, List<OrderLine> orderLines) {
        this.name = name;
        this.orderLines = orderLines;
    }

    public Order(){
    }
}
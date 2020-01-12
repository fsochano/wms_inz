package com.sochanski.order.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sochanski.sku.Sku;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "order_line")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_line_id_gen")
    @SequenceGenerator(name = "order_line_id_gen", sequenceName = "order_line_id_gen", allocationSize = 1)
    public long id;

    @Positive
    public long qty;

    @OneToOne
    @JoinColumn(name = "sku_id", nullable = false, updatable = false)
    public Sku sku;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_header_id", nullable = false, updatable = false)
    @LazyToOne(value = LazyToOneOption.PROXY)
    public Order order;


    public OrderLine(long qty, Sku sku, Order order) {
        this.qty = qty;
        this.order = order;
        this.sku = sku;
    }

    public OrderLine() {
    }
}

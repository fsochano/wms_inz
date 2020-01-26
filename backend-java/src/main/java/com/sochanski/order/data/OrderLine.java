package com.sochanski.order.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sochanski.sku.Sku;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order_line")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_line_id_gen")
    @SequenceGenerator(name = "order_line_id_gen", sequenceName = "order_line_id_gen", allocationSize = 1)
    private long id;

    @Positive
    private long qty;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sku_id", nullable = false, updatable = false)
    private Sku sku;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_header_id", nullable = false, updatable = false)
    private OrderHeader orderHeader;

    public OrderLine(long qty, Sku sku, OrderHeader orderHeader) {
        this.qty = qty;
        this.orderHeader = orderHeader;
        this.sku = sku;
    }
}

package com.sochanski.order;

import javax.persistence.*;

@Entity
@Table(name = "order_line")
public class OrderLine {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="order_line_id_gen")
    @SequenceGenerator(name="order_line_id_gen", sequenceName="order_line_id_gen", allocationSize=1)
    public long id;
    public long qty;
    public String item;

    public OrderLine(long qty, String item) {
        this.qty = qty;
        this.item = item;
    }

    public OrderLine() {
    }
}

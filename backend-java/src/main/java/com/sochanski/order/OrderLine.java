package com.sochanski.order;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OrderLine {

    @Id
    @GeneratedValue
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

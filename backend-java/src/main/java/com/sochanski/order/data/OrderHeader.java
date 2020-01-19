package com.sochanski.order.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_header")
public class OrderHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_header_id_gen")
    @SequenceGenerator(name = "order_header_id_gen", sequenceName = "order_header_id_gen", allocationSize = 1)
    public long id;

    @NotNull
    @NotEmpty
    public String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    public OrderHeaderStatus status;

    public String lastChangeBy;

    public LocalDateTime lastChangeDate;

    @JsonIgnore
    @OneToMany(mappedBy = "orderHeader", cascade = CascadeType.REMOVE)
    @LazyCollection(value = LazyCollectionOption.TRUE)
    public List<OrderLine> orderLines;

    //    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public OrderHeader(String name) {
        this(name, OrderHeaderStatus.HOLD, new ArrayList<>());
    }

    public OrderHeader(String name, OrderHeaderStatus status, List<OrderLine> orderLines) {
        this.name = name;
        this.status = status;
        this.orderLines = orderLines;
    }

    public OrderHeader() {
    }

}
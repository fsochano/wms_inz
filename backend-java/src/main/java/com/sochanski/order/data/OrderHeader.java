package com.sochanski.order.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order_header")
public class OrderHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_header_id_gen")
    @SequenceGenerator(name = "order_header_id_gen", sequenceName = "order_header_id_gen", allocationSize = 1)
    private long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderHeaderStatus status;

    private String lastChangeBy;

    private LocalDateTime lastChangeDate;

    @JsonIgnore
    @OneToMany(mappedBy = "orderHeader", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<OrderLine> orderLines = emptyList();

    public OrderHeader(String name) {
        this(name, OrderHeaderStatus.HOLD, new ArrayList<>());
    }

    public OrderHeader(String name, OrderHeaderStatus status, List<OrderLine> orderLines) {
        this.name = name;
        this.status = status;
        this.orderLines = orderLines;
    }

}
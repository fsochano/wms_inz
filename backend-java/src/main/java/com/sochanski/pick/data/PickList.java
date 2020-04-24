package com.sochanski.pick.data;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sochanski.db.Auditable;
import com.sochanski.order.data.OrderHeader;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.util.List;

import static java.util.Collections.emptyList;

@Data
@NoArgsConstructor
@Entity
@Table(name = "pick_list")
public class PickList extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pick_list_id_gen")
    @SequenceGenerator(name = "pick_list_id_gen", sequenceName = "pick_list_id_gen", allocationSize = 1)
    private long id;

    @Enumerated(EnumType.STRING)
    private PickListStatus status;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_header_id", nullable = false)
    @LazyToOne(value = LazyToOneOption.PROXY)
    private OrderHeader orderHeader;

    @JsonIgnore
    @OneToMany(mappedBy = "pickList", fetch = FetchType.LAZY)
    private List<PickTask> pickTasks = emptyList();

    public PickList(OrderHeader orderHeader) {
        this(orderHeader, PickListStatus.RELEASED);
    }

    public PickList(OrderHeader orderHeader, PickListStatus status) {
        this(orderHeader, status, emptyList());
    }

    public PickList(OrderHeader orderHeader, PickListStatus status, List<PickTask> pickTasks) {
        this.orderHeader = orderHeader;
        this.status = status;
        this.pickTasks = List.copyOf(pickTasks);
    }

    @Transient
    @JsonProperty
    public Long getOrderId(){
        return orderHeader.getId();
    }
}

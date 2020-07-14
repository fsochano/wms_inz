package com.sochanski.pick.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sochanski.container.Container;
import com.sochanski.db.Auditable;
import com.sochanski.order.data.OrderLine;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@Entity
@Table(name = "pick_task")
public class PickTask extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pick_task_id_gen")
    @SequenceGenerator(name = "pick_task_id_gen", sequenceName = "pick_task_id_gen", allocationSize = 1)
    private long id;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "pick_list_id", nullable = false)
    private PickList pickList;

    @Positive
    private long qty;

    @Enumerated(EnumType.STRING)
    private PickTaskStatus status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "from_container_id")
    private Container fromContainer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "to_container_id")
    private Container toContainer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, optional = false)
    @JoinColumn(name = "order_line_id", nullable = false, updatable = false)
    private OrderLine orderLine;

//    @JsonIgnore
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "sku_id", nullable = false, updatable = false)
//    private Sku sku;

    public PickTask(PickList pickList,
                    OrderLine orderLine,
                    long qty,
                    Container fromContainer,
                    Container toContainer) {
        this(pickList, orderLine, PickTaskStatus.READY, qty, fromContainer, toContainer);
    }

    public PickTask(PickList pickList,
                    OrderLine orderLine,
                    long qty,
                    PickTaskStatus status) {
        this(pickList, orderLine, status, qty, null, null);
    }

    public PickTask(PickList pickList,
                    OrderLine orderLine,
                    PickTaskStatus status,
                    long qty,
                    Container fromContainer,
                    Container toContainer) {
        this.pickList = pickList;
        this.orderLine = orderLine;
//        this.sku = orderLine.getSku();
        this.status = status;
        this.qty = qty;
        this.fromContainer = fromContainer;
        this.toContainer = toContainer;
    }

    @JsonProperty
    @Transient
    public Long getFromContainerId() {
        return fromContainer != null ? fromContainer.getId() : null;
    }

    @JsonProperty
    @Transient
    public String getFromLocationName() {
        return fromContainer != null ? fromContainer.getLocation().getName() : null;
    }

    @JsonProperty
    @Transient
    public Long getToContainerId() {
        return toContainer != null ? toContainer.getId() : null;
    }

    @JsonProperty
    @Transient
    public String getToLocationName() {
        return toContainer != null ? toContainer.getLocation().getName() : null;
    }

    @JsonProperty
    @Transient
    public String getSkuName() {
        return orderLine != null ? orderLine.getSku().getName() : null;
    }
}

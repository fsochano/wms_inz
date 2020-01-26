package com.sochanski.pick;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sochanski.container.Container;
import com.sochanski.order.data.OrderLine;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "pick_task")
public class PickTask {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pick_task_id_gen")
    @SequenceGenerator(name = "pick_task_id_gen", sequenceName = "pick_task_id_gen", allocationSize = 1)
    private long id;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "pick_list_id", nullable = false)
    private PickList pickList;

    private long qty;

    @Enumerated(EnumType.STRING)
    private PickTaskStatus status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_container_id")
    private Container fromContainer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_container_id")
    private Container toContainer;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_line_id", nullable = false)
    private OrderLine orderLine;

    public PickTask(PickList pickList,
                    OrderLine orderLine,
                    PickTaskStatus status,
                    long qty) {
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
        this.status = status;
        this.qty = qty;
        this.fromContainer = fromContainer;
        this.toContainer = toContainer;
    }

    @JsonProperty
    @Transient
    public Long getFromContainer() {
        return fromContainer != null ? fromContainer.getId() : null;
    }

    @JsonProperty
    @Transient
    public String getFromLocation() {
        return fromContainer != null ? fromContainer.getLocation().getName() : null;
    }

    @JsonProperty
    @Transient
    public Long getToContainer() {
        return toContainer != null ? toContainer.getId() : null;
    }

    @JsonProperty
    @Transient
    public String getToLocation() {
        return toContainer != null ? toContainer.getLocation().getName() : null;
    }

}

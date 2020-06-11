package com.sochanski.shipping;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sochanski.db.Auditable;
import com.sochanski.pick.data.PickList;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
@Table(name = "shipment")
public class Shipment extends Auditable {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="shipment_id_gen")
    @SequenceGenerator(name="shipment_id_gen", sequenceName="shipment_id_gen", allocationSize=1)
    private long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ShippingStatus status;

    @JsonIgnore
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "pick_list_id", nullable = false)
    @LazyToOne(value = LazyToOneOption.PROXY)
    private PickList pickList;

    public Shipment(PickList pickList){
        this(pickList, ShippingStatus.READY);
    }

    public Shipment(PickList pickList, ShippingStatus status) {
        this.pickList = pickList;
        this.status = status;
    }

    @Transient
    @JsonProperty
    public Long getPickListId(){
        return pickList.getId();
    }

    @Transient
    @JsonProperty
    public String getOrderName(){
        return pickList.getOrderHeader().getName();
    }
}

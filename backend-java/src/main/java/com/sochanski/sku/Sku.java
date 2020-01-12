package com.sochanski.sku;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "sku")
public class Sku {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="sku_id_gen")
    @SequenceGenerator(name="sku_id_gen", sequenceName="sku_id_gen", allocationSize=1)
    public long id;

    @NotEmpty
    public String name;

    public String description;

    public Sku() {}

    public Sku(String name) {
        this.name = name;
    }
    public Sku(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

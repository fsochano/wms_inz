package com.sochanski.sku;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@Entity
@Table(name = "sku")
public class Sku {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="sku_id_gen")
    @SequenceGenerator(name="sku_id_gen", sequenceName="sku_id_gen", allocationSize=1)
    private long id;

    @NotEmpty
    private String name;

    private String description;

    public Sku(String name) {
        this.name = name;
    }
    public Sku(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

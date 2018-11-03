package com.bilev.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="TARIFF")
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class Tariff extends AbstractModel {

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "INFO")
    private String info;

    @Column(name = "VALID")
    private Boolean valid;

    @OneToMany(mappedBy = "tariff")
    private List<Contract> contracts = new ArrayList<>();

    @OneToMany(mappedBy = "tariff",
            cascade = CascadeType.ALL
    )
    private List<Option> options = new ArrayList<>();


}

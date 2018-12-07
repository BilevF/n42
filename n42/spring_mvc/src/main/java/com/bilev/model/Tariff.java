package com.bilev.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="TARIFF")
@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
@ToString(callSuper = true, exclude =  {"contracts", "options"})
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
    private Set<Contract> contracts = new HashSet<>();

    @OneToMany(mappedBy = "tariff")
    private Set<Option> options = new HashSet<>();


}

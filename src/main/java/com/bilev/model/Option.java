package com.bilev.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="TARIFF_OPTION")
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class Option extends AbstractModel {

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "CONNECTION_PRICE")
    private Double connectionPrice;

    @Column(name = "INFO")
    private String info;

    @ManyToOne
    @JoinColumn(name = "TARIFF_ID")
    private Tariff tariff;

    @ManyToMany
    @JoinTable(name = "CONTRACT_OPTIONS",
            joinColumns = @JoinColumn(name = "OPTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "CONTRACT_ID")
    )
    private List<Contract> contracts = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "REQUIRED_OPTIONS",
            joinColumns = @JoinColumn(name = "FIRST_OPTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "SECOND_OPTION_ID")
    )
    private List<Option> requiredOptions = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "INCOMPATIBLE_OPTIONS",
            joinColumns = @JoinColumn(name = "FIRST_OPTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "SECOND_OPTION_ID")
    )
    private List<Option> incompatibleOptions = new ArrayList<>();

    // Hibernate hack

    @ManyToMany
    @JoinTable(name = "REQUIRED_OPTIONS",
            joinColumns = @JoinColumn(name = "SECOND_OPTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "FIRST_OPTION_ID")
    )
    private List<Option> requiredOptionsOf = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "INCOMPATIBLE_OPTIONS",
            joinColumns = @JoinColumn(name = "SECOND_OPTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "FIRST_OPTION_ID")
    )
    private List<Option> incompatibleOptionsOf = new ArrayList<>();

}

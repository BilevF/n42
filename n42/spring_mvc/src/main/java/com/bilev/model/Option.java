package com.bilev.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="TARIFF_OPTION")
@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
@ToString(callSuper = true, exclude =  {"tariff", "requiredOptions", "incompatibleOptions", "requiredOptionsOf", "incompatibleOptionsOf"})
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
    @JoinTable(name = "REQUIRED_OPTIONS",
            joinColumns = @JoinColumn(name = "FIRST_OPTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "SECOND_OPTION_ID")
    )
    private Set<Option> requiredOptions = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "INCOMPATIBLE_OPTIONS",
            joinColumns = @JoinColumn(name = "FIRST_OPTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "SECOND_OPTION_ID")
    )
    private Set<Option> incompatibleOptions = new HashSet<>();

    // Hibernate hack

    @ManyToMany
    @JoinTable(name = "REQUIRED_OPTIONS",
            joinColumns = @JoinColumn(name = "SECOND_OPTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "FIRST_OPTION_ID")
    )
    private Set<Option> requiredOptionsOf = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "INCOMPATIBLE_OPTIONS",
            joinColumns = @JoinColumn(name = "SECOND_OPTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "FIRST_OPTION_ID")
    )
    private Set<Option> incompatibleOptionsOf = new HashSet<>();

    @Transient
    private transient boolean availableForRemove;

}

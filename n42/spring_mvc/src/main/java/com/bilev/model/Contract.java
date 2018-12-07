package com.bilev.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="CONTRACT")
@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
@ToString(callSuper = true, exclude =  {"user", "tariff", "options", "basket", "histories"})
public class Contract extends AbstractModel {

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "BALANCE")
    private Double balance;

    @ManyToOne
    @JoinColumn(name = "BLOCK_ID")
    private Block block;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "TARIFF_ID")
    private Tariff tariff;

    @ManyToMany
    @JoinTable(name = "CONTRACT_OPTIONS",
            joinColumns = @JoinColumn(name = "CONTRACT_ID"),
            inverseJoinColumns = @JoinColumn(name = "OPTION_ID")
    )
    private Set<Option> options = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "BASKET",
            joinColumns = @JoinColumn(name = "CONTRACT_ID"),
            inverseJoinColumns = @JoinColumn(name = "OPTION_ID")
    )
    private Set<Option> basket = new HashSet<>();

    @OneToMany(mappedBy = "contract")
    private Set<History> histories = new HashSet<>();

}

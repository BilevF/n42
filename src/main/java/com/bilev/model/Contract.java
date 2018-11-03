package com.bilev.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="CONTRACT")
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
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
    private List<Option> options = new ArrayList<>();

}

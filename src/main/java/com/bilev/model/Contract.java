package com.bilev.model;

import com.bilev.model.enums.Blocked;
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
public class Contract extends AbstractPO {

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "BALANCE")
    private Double balance;

    @Column(name = "BLOCKED")
    @Enumerated(EnumType.STRING)
    private Blocked blocked;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Client client;

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

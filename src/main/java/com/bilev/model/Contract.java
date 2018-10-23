package com.bilev.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="CONTRACT")
@Setter
@Getter
@EqualsAndHashCode
public class Contract {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "TARIFF_ID")
    private Integer tariffId;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
}

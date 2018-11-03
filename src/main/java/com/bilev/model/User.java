package com.bilev.model;

import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="USER")
@Inheritance
@DiscriminatorColumn(name = "ROLE",
        discriminatorType = DiscriminatorType.STRING)
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractModel {

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "BIRTH_DATE")
    private Date birthDate;

    @Column(name = "PASSPORT")
    private String passport;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @OneToMany(mappedBy = "client",
            cascade = CascadeType.ALL
    )
    private List<Contract> contracts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;
}

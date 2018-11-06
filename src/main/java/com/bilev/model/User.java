package com.bilev.model;

import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="USER")
@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
@ToString(callSuper = true, exclude =  {"contracts", "role"})
public class User extends AbstractModel {

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Temporal(TemporalType.DATE)
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

    @OneToMany(mappedBy = "user")
    private Set<Contract> contracts = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;

}

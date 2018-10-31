package com.bilev.model;

import javax.persistence.*;

import com.bilev.model.enums.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;


import java.sql.Date;
import java.util.*;


@Entity
@Table(name="USER")
@Inheritance
@DiscriminatorColumn(name = "ROLE",
        discriminatorType = DiscriminatorType.STRING)
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractPO {

    @Column(name = "ROLE", insertable = false, updatable = false)
    private String role;

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

    transient private String confirmPassword;
}

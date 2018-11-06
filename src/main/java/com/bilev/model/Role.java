package com.bilev.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="ROLE")
@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
@ToString(callSuper = true, exclude =  {"users"})
public class Role extends AbstractModel {

    public enum RoleName {
        ROLE_CLIENT, ROLE_ADMIN
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "NAME")
    private RoleName roleName;

    @OneToMany(mappedBy = "role")
    private Set<User> users = new HashSet<>();
}

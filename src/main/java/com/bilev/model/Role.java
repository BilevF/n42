package com.bilev.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ROLE")
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class Role extends AbstractModel {

    public enum RoleName {
        ROLE_CLIENT, ROLE_ADMIN
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "NAME")
    private RoleName roleName;

    @OneToMany(mappedBy = "role")
    private List<User> users = new ArrayList<>();
}

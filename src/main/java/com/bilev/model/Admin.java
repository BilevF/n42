package com.bilev.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ROLE_ADMIN")
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {
}

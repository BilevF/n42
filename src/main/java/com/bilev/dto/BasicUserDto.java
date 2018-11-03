package com.bilev.dto;

import com.bilev.model.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class BasicUserDto extends AbstractDto {
    private String firstName;

    private String lastName;

    private Date birthDate;

    private String passport;

    private String address;

    private String email;

    private String password;

    private Role.RoleName roleRoleName;
}

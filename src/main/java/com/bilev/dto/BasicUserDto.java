package com.bilev.dto;

import com.bilev.model.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
public class BasicUserDto extends AbstractDto {
    private String firstName;

    private String lastName;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthDate;

    private String passport;

    private String address;

    private String email;

    private String password;

    private Role.RoleName roleRoleName;
}

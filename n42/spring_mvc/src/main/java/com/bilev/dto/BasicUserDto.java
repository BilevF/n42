package com.bilev.dto;

import com.bilev.model.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
public class BasicUserDto extends AbstractDto {

    @NotNull(message = "{NotNull.user.firstName}")
    @Size(min=2, max=45, message = "{Size.user.firstName}")
    private String firstName;

    @NotNull(message = "{NotNull.user.lastName}")
    @Size(min=2, max=45, message = "{Size.user.lastName}")
    private String lastName;

    @NotNull(message = "{NotNull.user.birthDate}")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthDate;

    @NotNull(message = "{NotNull.user.passport}")
    @Size(min=2, max=100, message = "{Size.user.passport}")
    private String passport;

    @NotNull(message = "{NotNull.user.address}")
    @Size(min=2, max=100, message = "{Size.user.address}")
    private String address;

    @NotNull(message = "{NotNull.user.email}")
    @Size(min=3, max=100, message = "{Size.user.email}")
    @Email
    private String email;

    @NotNull(message = "{NotNull.user.password}")
//    @Size(min=3, max=20)
    private String password;

    private Double balance;

    @JsonIgnore
    private Role.RoleName roleRoleName;
}

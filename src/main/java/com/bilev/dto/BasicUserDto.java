package com.bilev.dto;

import com.bilev.model.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
public class BasicUserDto extends AbstractDto {

    @NotNull
    @Size(min=2, max=45)
    private String firstName;

    @NotNull
    @Size(min=2, max=45)
    private String lastName;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthDate;

    @NotNull
    @Size(min=2, max=100)
    private String passport;

    @NotNull
    @Size(min=2, max=100)
    private String address;

    @NotNull
    @Size(min=2, max=100)
    @Email
    private String email;

    @NotNull
    @Size(min=3, max=20)
    private String password;

    private Role.RoleName roleRoleName;
}

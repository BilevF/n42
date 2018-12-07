package com.bilev.tools;

import com.bilev.dto.BasicUserDto;
import com.bilev.dto.UserDto;
import com.bilev.model.Role;
import com.bilev.model.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserCreator implements Creator<UserDto, BasicUserDto, User> {

    private final String firstName = "user";

    private final String lastName = "user";

    private final Date birthDate = new Date();

    private final String passport = "4010858585";

    private final String address = "Spb";

    private final String email = "user@mail.ru";

    private final String password = "333";


    @Override
    public UserDto getDto(int id) {
        UserDto userDto = new UserDto();

        userDto.setId(id);
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setBirthDate(birthDate);
        userDto.setPassport(passport);
        userDto.setAddress(address);
        userDto.setEmail(id + email);
        userDto.setPassword(password);

        return userDto;
    }

    @Override
    public BasicUserDto getBasicDto(int id) {
        BasicUserDto basicUserDto = new BasicUserDto();

        basicUserDto.setId(id);
        basicUserDto.setFirstName(firstName);
        basicUserDto.setLastName(lastName);
        basicUserDto.setBirthDate(birthDate);
        basicUserDto.setPassport(passport);
        basicUserDto.setAddress(address);
        basicUserDto.setEmail(id + email);
        basicUserDto.setPassword(password);

        return basicUserDto;
    }

    @Override
    public User getEntity(int id) {
        User user = new User();

        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBirthDate(birthDate);
        user.setPassport(passport);
        user.setAddress(address);
        user.setEmail(id + email);
        user.setPassword(password);

        return user;
    }

    public Role getRole(Role.RoleName roleName) {
        Role role = new Role();

        role.setId(0);
        role.setRoleName(roleName);

        return role;
    }

}

package com.bilev.service.api;

import com.bilev.dto.BasicContractDto;
import com.bilev.dto.BasicUserDto;
import com.bilev.dto.ContractDto;
import com.bilev.dto.UserDto;
import com.bilev.model.User;

import java.util.List;

public interface UserService {
    UserDto getUserByEmail(String email);

    UserDto getUserById(int userId);

    List<BasicUserDto> getAllUsers();

    int saveUser(BasicUserDto basicUserDto);

    int saveContract(BasicContractDto contractDto);

}

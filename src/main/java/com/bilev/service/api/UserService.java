package com.bilev.service.api;

import com.bilev.dto.BasicContractDto;
import com.bilev.dto.BasicUserDto;
import com.bilev.dto.ContractDto;
import com.bilev.dto.UserDto;
import com.bilev.exception.NotFoundException;
import com.bilev.exception.UnableToSaveException;
import com.bilev.model.User;

import java.util.List;

public interface UserService {
    UserDto getUserByEmail(String email) throws NotFoundException;

    UserDto getUserById(int userId) throws NotFoundException;

    List<BasicUserDto> getAllUsers();

    int findUserByPhone(String phone) throws NotFoundException;

    int saveUser(BasicUserDto basicUserDto) throws UnableToSaveException;

    int saveContract(BasicContractDto contractDto) throws UnableToSaveException, NotFoundException;

}

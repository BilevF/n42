package com.bilev.service.api;

import com.bilev.dto.BasicUserDto;
import com.bilev.dto.UserDto;
import com.bilev.exception.service.OperationFailed;

import java.util.List;

public interface UserService {

    UserDto getUserByEmail(String email) throws OperationFailed;

    UserDto getClientByEmail(String email) throws OperationFailed;

    UserDto getUserById(int userId) throws OperationFailed;

    UserDto getClientById(int userId) throws OperationFailed;

    List<BasicUserDto> getAllClients() throws OperationFailed;

    UserDto getUserByPhone(String phone) throws OperationFailed;

    int saveUser(BasicUserDto basicUserDto) throws OperationFailed;

}

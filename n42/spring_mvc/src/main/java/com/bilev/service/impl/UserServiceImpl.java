package com.bilev.service.impl;

import com.bilev.dao.api.ContractDao;
import com.bilev.dao.api.RoleDao;
import com.bilev.dao.api.UserDao;
import com.bilev.dto.BasicUserDto;
import com.bilev.dto.UserDto;
import com.bilev.exception.dao.UnableToFindException;
import com.bilev.exception.dao.UnableToSaveException;

import com.bilev.exception.service.OperationFailed;
import com.bilev.exception.service.ServiceErrors;
import com.bilev.model.Contract;
import com.bilev.model.Role;
import com.bilev.model.User;
import com.bilev.service.api.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.Validator;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService, ServiceErrors {

    private final UserDao userDao;

    private final RoleDao roleDao;

    private final ModelMapper modelMapper;

    private final ContractDao contractDao;

    private final ShaPasswordEncoder passwordEncoder;

    private final Validator validator;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, ModelMapper modelMapper, ContractDao contractDao,
                           ShaPasswordEncoder passwordEncoder, Validator validator) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.modelMapper = modelMapper;
        this.contractDao = contractDao;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByEmail(String email) throws OperationFailed {

        try {
            if (email == null) throw new OperationFailed(USER_NOT_FOUND);

            User user = userDao.findByEmail(email);
            if (user == null) throw new OperationFailed(USER_NOT_FOUND);

            return modelMapper.map(
                    user,
                    UserDto.class);

        } catch (UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_FIND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getClientByEmail(String email) throws OperationFailed {

        try {
            if (email == null) throw new OperationFailed(USER_NOT_FOUND);

            User user = userDao.findClientByEmail(email);
            if (user == null) throw new OperationFailed(USER_NOT_FOUND);

            return modelMapper.map(
                    user,
                    UserDto.class);

        } catch (UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_FIND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(int userId) throws OperationFailed {
        try {
            User user = userDao.getByKey(userId);
            if (user == null) throw new OperationFailed(USER_NOT_FOUND);

            return modelMapper.map(
                    user,
                    UserDto.class);

        } catch (UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_FIND);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getClientById(int userId) throws OperationFailed {
        try {
            User user = userDao.getClientById(userId);
            if (user == null) throw new OperationFailed(USER_NOT_FOUND);

            return modelMapper.map(
                    user,
                    UserDto.class);

        } catch (UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_FIND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BasicUserDto> getAllClients() throws OperationFailed {
        try {
            List<User> users = userDao.getAllClients();

            return modelMapper.map(
                    users,
                    new TypeToken<List<BasicUserDto>>() {}.getType());
        } catch (UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_FIND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByPhone(String phone) throws OperationFailed {
        try {
            if (phone == null) throw new OperationFailed(USER_NOT_FOUND);

            Contract contract = contractDao.getContractByPhone(phone);
            if (contract == null) throw new OperationFailed(USER_NOT_FOUND);

            return modelMapper.map(
                    contract.getUser(),
                    UserDto.class);

        } catch (UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_FIND);
        }

    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int saveUser(@Valid BasicUserDto basicUserDto) throws OperationFailed {
        try {

            if (!validator.validate(basicUserDto).isEmpty()) throw new OperationFailed(VALIDATION);

            User hasUser = userDao.findByEmail(basicUserDto.getEmail());
            if (hasUser != null) throw new OperationFailed(EMAIL_NOT_UNIQUE);

            Role role = roleDao.getRoleByName(Role.RoleName.ROLE_CLIENT);
            if (role == null) throw new OperationFailed(UNABLE_TO_SAVE);

            User user = modelMapper.map(
                    basicUserDto,
                    User.class);

            user.setRole(role);

            user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));

            userDao.persist(user);

            return user.getId();

        } catch (UnableToFindException | UnableToSaveException e) {
            throw new OperationFailed(UNABLE_TO_SAVE);
        }
    }

}

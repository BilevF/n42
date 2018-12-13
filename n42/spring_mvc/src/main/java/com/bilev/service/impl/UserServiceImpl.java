package com.bilev.service.impl;

import com.bilev.dao.api.ContractDao;
import com.bilev.dao.api.HistoryDao;
import com.bilev.dao.api.RoleDao;
import com.bilev.dao.api.UserDao;
import com.bilev.dto.BasicUserDto;
import com.bilev.dto.UserDto;
import com.bilev.exception.dao.UnableToFindException;
import com.bilev.exception.dao.UnableToSaveException;

import com.bilev.exception.dao.UnableToUpdateException;
import com.bilev.exception.service.OperationFailed;
import com.bilev.exception.service.ServiceErrors;
import com.bilev.model.Contract;
import com.bilev.model.History;
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

import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService, ServiceErrors {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private HistoryDao historyDao;

    @Autowired
    private ShaPasswordEncoder passwordEncoder;

    @Autowired
    private Validator validator;

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
    public List<UserDto> getAllClients() throws OperationFailed {
        try {
            List<User> users = userDao.getAllClients();

            return modelMapper.map(
                    users,
                    new TypeToken<List<UserDto>>() {}.getType());
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

            user.setPassword("");

            user.setBalance(0.0);

            userDao.persist(user);

            return user.getId();

        } catch (UnableToFindException | UnableToSaveException e) {
            throw new OperationFailed(UNABLE_TO_SAVE);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void updateUser(BasicUserDto basicUserDto) throws OperationFailed {
        try {

            if (!validator.validate(basicUserDto).isEmpty() || basicUserDto.getId() == null)
                throw new OperationFailed(VALIDATION);

            User hasUser = userDao.findByEmail(basicUserDto.getEmail());
            if (hasUser != null && hasUser.getId() != basicUserDto.getId())
                throw new OperationFailed(EMAIL_NOT_UNIQUE);

            User user = userDao.getByKey(basicUserDto.getId());
            if (user == null) throw new OperationFailed(USER_NOT_FOUND);

            user.setEmail(basicUserDto.getEmail());
            user.setAddress(basicUserDto.getAddress());

            if (!basicUserDto.getPassword().isEmpty())
                user.setPassword(basicUserDto.getPassword());

            userDao.update(user);

        } catch (UnableToFindException | UnableToUpdateException e) {
            throw new OperationFailed(UNABLE_TO_UPDATE);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void updateUserPassword(int userId, String password) throws OperationFailed {
        try {

            if (password == null || password.isEmpty()) throw new OperationFailed(UNABLE_TO_UPDATE);

            User user = userDao.getByKey(userId);
            if (user == null) throw new OperationFailed(USER_NOT_FOUND);

            user.setPassword(password);

            userDao.update(user);

        } catch (UnableToFindException | UnableToUpdateException e) {
            throw new OperationFailed(UNABLE_TO_UPDATE);
        }
    }




    @Override
    @Transactional(rollbackFor=Exception.class)
    public void addMoney(int userId, double amount) throws OperationFailed {

        try {
            User user = userDao.getByKey(userId);

            if (amount < 0) throw new OperationFailed(UNABLE_TO_UPDATE);

            user.setBalance(user.getBalance() + amount);

            userDao.update(user);

            Date date = new Date();
            for (Contract  contract : user.getContracts()) {
                History history = new History();
                history.setDate(date);
                history.setName("Top up balance");
                history.setPrice(amount);
                history.setContract(contract);
                historyDao.persist(history);
            }

        } catch (UnableToUpdateException | UnableToSaveException | UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_UPDATE);
        }
    }



}

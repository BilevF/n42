package com.bilev.service.impl;

import com.bilev.dao.api.BlockDao;
import com.bilev.dao.api.ContractDao;
import com.bilev.dao.api.RoleDao;
import com.bilev.dao.api.UserDao;
import com.bilev.dto.BasicContractDto;
import com.bilev.dto.BasicUserDto;
import com.bilev.dto.ContractDto;
import com.bilev.dto.UserDto;
import com.bilev.model.*;
import com.bilev.service.api.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ShaPasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByEmail(String email) {
        User user = userDao.findByEmail(email);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(int userId) {
        User user = userDao.getByKey(userId);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BasicUserDto> getAllUsers() {
        List<User> users = userDao.getAllUsers();

        return modelMapper.map(users, new TypeToken<List<BasicUserDto>>() {}.getType());
    }

    @Override
    @Transactional
    public int saveUser(BasicUserDto basicUserDto) {
        User user = modelMapper.map(basicUserDto, User.class);

        if (user.getRole() == null) {
            user.setRole(roleDao.getRoleByName(Role.RoleName.ROLE_CLIENT));
        }

        user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));

        userDao.saveOrUpdate(user);

        return  user.getId();
    }

    @Override
    @Transactional
    public int saveContract(BasicContractDto contractDto) {
        Contract contract = modelMapper.map(contractDto, Contract.class);

        if (contract.getBalance() == null) contract.setBalance(0.0);
        if (contract.getBlock() == null) contract.setBlock(blockDao.getBlockByType(Block.BlockType.NON));

        contractDao.saveOrUpdate(contract);

        return contract.getId();
    }
}

package com.bilev.service.impl;

import com.bilev.dao.api.UserDao;
import com.bilev.dto.BasicUserDto;
import com.bilev.dto.UserDto;
import com.bilev.model.User;
import com.bilev.service.api.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userDao.find(email);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserById(int userId) {
        User user = userDao.findById(userId);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<BasicUserDto> getAllUsers() {
        List<User> users = userDao.getAllUsers();

        return modelMapper.map(users, new TypeToken<List<BasicUserDto>>() {}.getType());
    }
}

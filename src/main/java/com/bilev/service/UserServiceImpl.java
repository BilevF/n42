package com.bilev.service;

import com.bilev.dao.UserDao;
import com.bilev.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findUser(String email) {
        return userDao.find(email);
    }

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }
}

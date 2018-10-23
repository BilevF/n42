package com.bilev.service;

import com.bilev.dao.UserDao;
import com.bilev.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("authService")
@Transactional
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserDao userDao;

    @Override
    public boolean findUser(String email, String password) {
        User user = userDao.find(email, password);
        return user != null;
    }
}

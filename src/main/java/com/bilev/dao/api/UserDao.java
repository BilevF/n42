package com.bilev.dao.api;

import com.bilev.model.User;

import java.util.List;

public interface UserDao extends AbstractDao<Integer, User> {

    User findByEmail(String email);

    List<User> getAllUsers();

}

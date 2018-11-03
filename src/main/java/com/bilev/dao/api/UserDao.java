package com.bilev.dao.api;

import com.bilev.model.User;

import java.util.List;

public interface UserDao {

    User find(String email);

    User findById(int id);

    List<User> getAllUsers();
}

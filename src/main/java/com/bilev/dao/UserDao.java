package com.bilev.dao;

import com.bilev.model.User;

public interface UserDao {

    User find(String email);

    User findById(int id);

}

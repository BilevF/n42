package com.bilev.service;

import com.bilev.model.User;

public interface UserService {
    User findUser(String email);

    User findById(int id);
}

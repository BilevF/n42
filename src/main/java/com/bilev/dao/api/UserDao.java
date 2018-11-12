package com.bilev.dao.api;

import com.bilev.exception.NotFoundException;
import com.bilev.model.User;

import java.util.List;

public interface UserDao extends AbstractDao<Integer, User> {

    User findByEmail(String email) throws NotFoundException;

    User findClientByEmail(String email) throws NotFoundException;

    List<User> getAllUsers();

}

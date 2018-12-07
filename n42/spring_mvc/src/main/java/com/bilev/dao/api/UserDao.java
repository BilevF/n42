package com.bilev.dao.api;

import com.bilev.exception.dao.UnableToFindException;
import com.bilev.model.User;

import java.util.List;

public interface UserDao extends AbstractDao<Integer, User> {

    User findByEmail(String email) throws UnableToFindException;

    User findClientByEmail(String email) throws UnableToFindException;

    User getClientById(int id) throws UnableToFindException;

    List<User> getAllClients() throws UnableToFindException;

}

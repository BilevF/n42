package com.bilev.service;

public interface AuthService {
    boolean findUser(String email, String password);
}

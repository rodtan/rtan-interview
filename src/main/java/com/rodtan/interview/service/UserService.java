package com.rodtan.interview.service;

import com.rodtan.interview.model.User;

import java.util.List;

public interface UserService {
    User getUser(int id);
    User addUser(User user);
    List<User> getAllUsers();
    User updateUser(User user);
    void deleteUser(int id);
}

package com.threadhive.services.interfaces;

import java.util.List;

import com.threadhive.models.User;

public interface UserService {
    public List<User> getAllUsers();
    public User createUser(User user);
}

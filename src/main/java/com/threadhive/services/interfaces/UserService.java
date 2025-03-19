package com.threadhive.services.interfaces;

import java.util.List;

import com.threadhive.dtos.UserDto;
import com.threadhive.models.User;

public interface UserService {
    public List<UserDto> getAllUsers();
    public User createUser(User user);
}

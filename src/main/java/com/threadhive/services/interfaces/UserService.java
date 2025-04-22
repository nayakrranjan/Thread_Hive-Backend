package com.threadhive.services.interfaces;

import java.util.List;
import java.util.UUID;

import com.threadhive.dtos.UserDto;
import com.threadhive.models.User;

public interface UserService {
    public List<UserDto> getAllUsers();
    public UserDto getUserById(UUID userId) throws Exception;
    public UserDto createUser(User user);
    public UserDto updateUser(UUID userId, User updateRequest) throws Exception;
}

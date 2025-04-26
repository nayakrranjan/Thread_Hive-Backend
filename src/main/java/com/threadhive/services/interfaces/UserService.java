package com.threadhive.services.interfaces;

import java.util.List;
import java.util.UUID;

import com.threadhive.dtos.UserDTO;
import com.threadhive.models.User;

public interface UserService {
    public List<UserDTO> getAllUsers();
    public UserDTO getUserById(UUID userId) throws Exception;
    public UserDTO createUser(User user);
    public UserDTO updateUser(UUID userId, User updateRequest) throws Exception;
}

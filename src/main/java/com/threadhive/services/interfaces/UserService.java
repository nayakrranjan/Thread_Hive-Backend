package com.threadhive.services.interfaces;

import java.util.List;
import java.util.UUID;

import com.threadhive.dtos.request.UserRequest;
import com.threadhive.dtos.response.UserResponse;

public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserById(UUID userId) throws Exception;
    UserResponse createUser(UserRequest user);
    UserResponse updateUser(UUID userId, UserRequest updateRequest) throws Exception;
}

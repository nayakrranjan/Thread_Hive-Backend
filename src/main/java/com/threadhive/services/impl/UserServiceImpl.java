package com.threadhive.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threadhive.dtos.UserDto;
import com.threadhive.models.User;
import com.threadhive.repositories.UserRepository;
import com.threadhive.services.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {

        List<UserDto> users = userRepository.findAll().stream().map(
            user -> new UserDto(
                user.getId(), 
                user.getUsername(), 
                user.getEmail(), 
                user.getName(),
                user.getProfilePhoto(), 
                user.getBackGroundPhoto(),
                user.getCreatedDate(), 
                user.getLastModifiedDate()
            )
        ).collect(Collectors.toList());

        return users;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
    
}

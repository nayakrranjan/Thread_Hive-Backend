package com.threadhive.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.threadhive.dtos.UserDto;
import com.threadhive.exceptions.AuthenticationException;
import com.threadhive.exceptions.DuplicateUserException;
import com.threadhive.exceptions.UserNotFoundException;
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
                user.getBackGroundPhoto()
                // user.getCreatedDate(), 
                // user.getLastModifiedDate()
            )
        ).collect(Collectors.toList());

        return users;
    }

    @Override
    public ResponseEntity<?> createUser(User user) {

        Map<String, String> errors = new HashMap<>();

        try {

            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                errors.put("username", "Username is required");
            } else {
                if (user.getUsername().startsWith(".") || user.getUsername().endsWith(".") || 
                    user.getUsername().contains("..")) {
                        errors.put("username", "Username cannot start or end with a period or contain consecutive periods");
                }
    
                if (!user.getUsername().matches("^[a-zA-Z0-9._]+$")) {
                    errors.put("username", "Username can only contain letters, numbers, periods and underscores");
                }
    
                if (user.getUsername().length() < 3 || user.getUsername().length() > 30) {
                    errors.put("username", "Username must be between 3 and 30 characters");
                }
    
                if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                    errors.put("email", "Email is required");
                } else {
                    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                    if (!user.getEmail().matches(emailRegex)) {
                        errors.put("email", "Please provide a valid email address");
                    }
                }
            }
    
            if (!errors.isEmpty()) {
                
                return ResponseEntity.badRequest().body(errors);
            }

            if (userRepository.existsByEmail(user.getEmail()))
                throw new DuplicateUserException("Email already in use. Please use a different one.");

            
            if (userRepository.existsByUsername(user.getUsername()))
                throw new DuplicateUserException("Username unavailable. Please choose another.");


            // User newUser = userRepository.save(user);
            // return new UserDto(
            //     newUser.getId(),
            //     newUser.getUsername(),
            //     newUser.getEmail(),
            //     newUser.getName(),
            //     newUser.getProfilePhoto(),
            //     newUser.getBackGroundPhoto()
            // );

            return null;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public UserDto updateUser(UUID userId, User updateRequest) {

        try {

            User foundUser = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with ID " + userId + " not found")
            );

            if (updateRequest.getPassword().equals(foundUser.getPassword()))
                throw new AuthenticationException("Invalid password!");

            if (updateRequest.getEmail() != null && updateRequest.getEmail() != foundUser.getEmail() 
                && !userRepository.existsByEmail(updateRequest.getEmail())) {
                    throw new DuplicateUserException("Email already registered with another user");
            }

            if (updateRequest.getUsername() != null && updateRequest.getUsername() != foundUser.getUsername() 
                && !userRepository.existsByUsername(updateRequest.getUsername())) {
                    throw new DuplicateUserException("Username Unavailable");
            }

            if (updateRequest.getUsername() != null) {
                foundUser.setUsername(updateRequest.getUsername());
            }
            
            if (updateRequest.getEmail() != null) {
                foundUser.setEmail(updateRequest.getEmail());
            }
            
            if (updateRequest.getName() != null) {
                foundUser.setName(updateRequest.getName());
            }
            
            if (updateRequest.getProfilePhoto() != null) {
                foundUser.setProfilePhoto(updateRequest.getProfilePhoto());
            }
            
            if (updateRequest.getBackGroundPhoto() != null) {
                foundUser.setBackGroundPhoto(updateRequest.getBackGroundPhoto());
            }
            
            userRepository.save(foundUser);

            return new UserDto(
                foundUser.getId(),
                foundUser.getUsername(),
                foundUser.getEmail(),
                foundUser.getName(),
                foundUser.getProfilePhoto(),
                foundUser.getBackGroundPhoto()
            );
        } catch (Exception ex) {
            throw ex;
        }
    }   
}

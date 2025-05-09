package com.threadhive.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.threadhive.dtos.request.UserRequest;
import com.threadhive.dtos.response.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.threadhive.exceptions.AuthenticationException;
import com.threadhive.exceptions.DuplicateUserException;
import com.threadhive.exceptions.InvalidInputException;
import com.threadhive.exceptions.UserNotFoundException;
import com.threadhive.models.User;
import com.threadhive.repositories.UserRepository;
import com.threadhive.services.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll().stream().map(
            user -> new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getProfilePhoto(),
                user.getBackgroundPhoto(),
                user.getCreatedDate(),
                user.getLastModifiedDate()
            )
        ).collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(UUID userId) {

        User foundUser = userRepository.findById(userId).orElseThrow(
            () -> new UserNotFoundException("Server Error", new HashMap<>(Map.of("id", "User not Found")))
        );

        return new UserResponse(
            foundUser.getId(),
            foundUser.getUsername(),
            foundUser.getEmail(),
            foundUser.getFirstName(),
            foundUser.getLastName(),
            foundUser.getProfilePhoto(),
            foundUser.getBackgroundPhoto(),
            foundUser.getCreatedDate(),
            foundUser.getLastModifiedDate()
        );
    }

    @Override
    public UserResponse getByUsername(String username) {

        User foundUser = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Server Error", new HashMap<>(Map.of("username", "User not Found")))
        );

        return new UserResponse(
                foundUser.getId(),
                foundUser.getUsername(),
                foundUser.getEmail(),
                foundUser.getFirstName(),
                foundUser.getLastName(),
                foundUser.getProfilePhoto(),
                foundUser.getBackgroundPhoto(),
                foundUser.getCreatedDate(),
                foundUser.getLastModifiedDate()
        );
    }

    @Override
    public UserResponse createUser(UserRequest user) {

        Map<String, String> errors = new HashMap<>();

        //validate username
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            errors.put("username", "Username is required");
        } else if (user.getUsername().length() < 3 || user.getUsername().length() > 30) {
            errors.put("username", "Username must be between 3 and 30 characters");
        } else if (user.getUsername().startsWith(".") || user.getUsername().endsWith(".") ||
            user.getUsername().contains("..") || !user.getUsername().matches("^[a-zA-Z0-9._]+$")) {
            errors.put("username", "Invalid username. Use only letters, numbers, periods, and underscores without consecutive or leading/trailing periods.");
        }

        //validate email
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            errors.put("email", "Email is required");
        } else if (!user.getEmail().matches(this.emailRegex)) {
            errors.put("email", "Please provide a valid email address");
        }

        if (!errors.isEmpty()) throw new InvalidInputException("Invalid Input", errors);

        Map<String, String> errors2 = new HashMap<>();

        if (userRepository.existsByEmail(user.getEmail()))
            errors2.put("email", "Email already in use. Please use a different one");

        if (userRepository.existsByUsername(user.getUsername()))
            errors2.put("username", "Username unavailable. Please choose another");

        if (!errors2.isEmpty()) throw new DuplicateUserException("Duplicate Data", errors2);

        User newUser = new User(
                null,
                user.getUsername(),
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()),
                user.getFirstName(),
                user.getLastName(),
                user.getProfilePhotoUrl(),
                user.getBackGroundPhotoUrl(),
                null,
                null
        );

        newUser = userRepository.save(newUser);

        return new UserResponse(
            newUser.getId(), newUser.getUsername(), newUser.getEmail(),
            newUser.getFirstName(), newUser.getLastName(), newUser.getProfilePhoto(),
            newUser.getBackgroundPhoto(), newUser.getCreatedDate(), newUser.getLastModifiedDate()
        );

    }

    @Override
    public UserResponse updateUser(UUID userId, UserRequest updateRequest) {

        User foundUser = userRepository.findById(userId).orElseThrow(
            () -> new UserNotFoundException("Server Error", new HashMap<>(Map.of("id", "User not Found")))
        );

        if (!passwordEncoder.encode(updateRequest.getPassword()).equals(foundUser.getPasswordHash()))
            throw new AuthenticationException("Unauthorised", new HashMap<>(Map.of("password", "Invalid Password")));

        Map<String, String> errors = new HashMap<>();
        Map<String, String> errors2 = new HashMap<>();

        if (updateRequest.getEmail() != null && !updateRequest.getEmail().equals(foundUser.getEmail())) {
            if (!updateRequest.getEmail().matches(this.emailRegex)) {
                errors.put("email", "Please provide a valid email address");
            } else if (userRepository.existsByEmail(updateRequest.getEmail())) {
                errors2.put("email", "Email already registered with another user");
            }
        }

        if (updateRequest.getUsername() != null && !updateRequest.getUsername().equals(foundUser.getUsername())) {
            if (updateRequest.getUsername().length() < 3 || updateRequest.getUsername().length() > 30) {
                errors.put("username", "Username must be between 3 and 30 characters");
            } else if (updateRequest.getUsername().startsWith(".") || updateRequest.getUsername().endsWith(".") ||
            updateRequest.getUsername().contains("..") || !updateRequest.getUsername().matches("^[a-zA-Z0-9._]+$")) {
                errors.put("username", "Invalid username. Use only letters, numbers, periods, and underscores without consecutive or leading/trailing periods.");
            } else if (userRepository.existsByUsername(updateRequest.getUsername())) {
                errors2.put("username", "Username Unavailable");
            }
        }

        if (!errors.isEmpty()) throw new InvalidInputException("Invalid Input", errors);
        if (!errors2.isEmpty()) throw new DuplicateUserException("Duplicate Data", errors2);


        if (updateRequest.getUsername() != null && !updateRequest.getUsername().isEmpty()
                && !updateRequest.getUsername().equals(foundUser.getUsername())) {
            foundUser.setUsername(updateRequest.getUsername());
        }

        if (updateRequest.getEmail() != null && !updateRequest.getEmail().isEmpty()
                && !updateRequest.getEmail().equals(foundUser.getEmail())) {
            foundUser.setEmail(updateRequest.getEmail());
        }

        if (updateRequest.getFirstName() != null && !updateRequest.getFirstName().isEmpty()
                && !updateRequest.getFirstName().equals(foundUser.getFirstName())) {
            foundUser.setFirstName(updateRequest.getFirstName());
        }

        if (updateRequest.getLastName() != null && !updateRequest.getLastName().isEmpty()
                && !updateRequest.getLastName().equals(foundUser.getLastName())) {
            foundUser.setLastName(updateRequest.getLastName());
        }

        if (updateRequest.getProfilePhotoUrl() != null && !updateRequest.getProfilePhotoUrl().isEmpty()
                && !updateRequest.getProfilePhotoUrl().equals(foundUser.getProfilePhoto())) {
            foundUser.setProfilePhoto(updateRequest.getProfilePhotoUrl());
        }

        if (updateRequest.getBackGroundPhotoUrl() != null && !updateRequest.getBackGroundPhotoUrl().isEmpty()
                && !updateRequest.getBackGroundPhotoUrl().equals(foundUser.getBackgroundPhoto())) {
            foundUser.setBackgroundPhoto(updateRequest.getBackGroundPhotoUrl());
        }

        userRepository.save(foundUser);

        return new UserResponse(
            foundUser.getId(),
            foundUser.getUsername(),
            foundUser.getEmail(),
            foundUser.getFirstName(),
            foundUser.getLastName(),
            foundUser.getProfilePhoto(),
            foundUser.getBackgroundPhoto(),
            foundUser.getCreatedDate(),
            foundUser.getLastModifiedDate()
        );
    }   
}

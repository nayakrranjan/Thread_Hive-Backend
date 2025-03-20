package com.threadhive.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.threadhive.models.User;
import com.threadhive.repositories.UserRepository;
import com.threadhive.services.interfaces.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {
    UserService userService;
    UserRepository userRepository;

    UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
    
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        var returnData = userService.getAllUsers();

        if (returnData == null || returnData.isEmpty()) return ResponseEntity.notFound().build();
        else return ResponseEntity.status(HttpStatus.FOUND).body(returnData);
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable UUID userId, @Valid @RequestBody User user) throws Exception {
        return new ResponseEntity<>(userService.updateUser(userId, user), HttpStatus.OK);
    }
    
    
}

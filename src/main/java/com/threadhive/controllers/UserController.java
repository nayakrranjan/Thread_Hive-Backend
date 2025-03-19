package com.threadhive.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.threadhive.models.User;
import com.threadhive.services.interfaces.UserService;




@RestController
public class UserController {
    UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        var returnData = userService.getAllUsers();

        if (returnData == null || returnData.isEmpty()) return ResponseEntity.notFound().build();
        else return ResponseEntity.status(HttpStatus.FOUND).body(returnData);
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Unknown error occured");
        }
    }

    @PutMapping("users//{id}")
    public String putMethodName(@PathVariable String id, @RequestBody String entity) {        
        return entity;
    }
    
    
}

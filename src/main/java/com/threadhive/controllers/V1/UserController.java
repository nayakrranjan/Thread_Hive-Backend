package com.threadhive.controllers.V1;

import java.util.UUID;

import com.threadhive.dtos.request.UserRequest;
import com.threadhive.dtos.response.UserResponse;
import com.threadhive.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.threadhive.repositories.UserRepository;
import com.threadhive.services.interfaces.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    UserService userService;
    UserRepository userRepository;

    UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
        UUID userId = ((CustomUserDetails) userDetails).getId();
        return ResponseEntity.ok(userService.getUserById(userId));
    }
    
    // @GetMapping("/get")
    public ResponseEntity<?> getAllUsers() {
        var returnData = userService.getAllUsers();

        if (returnData == null || returnData.isEmpty()) return ResponseEntity.notFound().build();
        else return ResponseEntity.status(HttpStatus.FOUND).body(returnData);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) throws Exception {
        var returnData = userService.getUserById(id);

        if (returnData == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.status(HttpStatus.FOUND).body(returnData);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @Valid @RequestBody UserRequest user) throws Exception {
        return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
    }
    
    
}

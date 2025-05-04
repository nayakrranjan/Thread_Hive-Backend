package com.threadhive.controllers.v1;

import com.threadhive.dtos.request.AuthenticationRequest;
import com.threadhive.dtos.request.UserRequest;
import com.threadhive.dtos.response.AuthenticationResponse;
import com.threadhive.dtos.response.UserResponse;
import com.threadhive.services.interfaces.AuthenticationService;
import com.threadhive.services.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest user) {
        UserResponse newUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody AuthenticationRequest authRequest) {
        AuthenticationResponse authResponse = authService.authenticate(authRequest);
        return ResponseEntity.ok(authResponse);
    }
}

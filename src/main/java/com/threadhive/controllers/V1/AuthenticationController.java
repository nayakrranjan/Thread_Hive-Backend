package com.threadhive.controllers.V1;

import com.threadhive.dtos.AuthenticationRequest;
import com.threadhive.dtos.AuthenticationResponse;
import com.threadhive.dtos.UserDTO;
import com.threadhive.models.User;
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
@RequestMapping("/api/V1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody User user) {
        UserDTO userDTO = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody AuthenticationRequest authRequest) {
        AuthenticationResponse authResponse = authService.authenticate(authRequest);
        return ResponseEntity.ok(authResponse);
    }
}

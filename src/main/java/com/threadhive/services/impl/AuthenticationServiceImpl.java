package com.threadhive.services.impl;

import com.threadhive.dtos.request.AuthenticationRequest;
import com.threadhive.dtos.response.AuthenticationResponse;
import com.threadhive.exceptions.AuthenticationException;
import com.threadhive.models.User;
import com.threadhive.repositories.UserRepository;
import com.threadhive.security.CustomUserDetails;
import com.threadhive.security.JwtUtility;
import com.threadhive.services.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtility utility;
    private final UserRepository userRepository;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = utility.generateToken(userDetails.getUsername());

            User user = userRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return new AuthenticationResponse(token, user.getId(), user.getUsername(), user.getEmail());

        } catch (Exception e) {
            throw new AuthenticationException("Invalid username/password combination");
        }
    }
}

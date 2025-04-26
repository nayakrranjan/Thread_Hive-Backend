package com.threadhive.services.impl;

import com.threadhive.exceptions.UserNotFoundException;
import com.threadhive.models.User;
import com.threadhive.repositories.UserRepository;
import com.threadhive.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Custom implementation of Spring Security's UserDetailsService.
 * This service is responsible for loading user details from the repository
 * by either the username or user ID (UUID). It returns a CustomUserDetails object
 * that Spring Security uses to perform authentication and authorization.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException(username));
        return new CustomUserDetails(user);
    }

    public CustomUserDetails loadUserById(UUID id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow( ()
                -> new UserNotFoundException("User - " + id + " not found."));
        return new CustomUserDetails(user);
    }
}

package com.threadhive.security;

import com.threadhive.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * Custom implementation of Spring Security's UserDetails interface.
 * Wraps around the User entity and provides authentication information to Spring Security.
 */
public class CustomUserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private final User user;

    /**
     * Constructs a UserDetails object wrapping the given User entity.
     *
     * @param user The User entity containing user authentication details.
     */
    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Returns the roles/authorities granted to the user, Currently hardcoded to ROLE_USER.
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return org.springframework.security.core.userdetails.UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return org.springframework.security.core.userdetails.UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return org.springframework.security.core.userdetails.UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return org.springframework.security.core.userdetails.UserDetails.super.isEnabled();
    }

    public UUID getId() {
        return this.user.getId();
    }
}

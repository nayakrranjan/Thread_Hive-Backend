package com.threadhive.services.interfaces;

import com.threadhive.dtos.AuthenticationRequest;
import com.threadhive.dtos.AuthenticationResponse;

public interface AuthenticationService {
    public AuthenticationResponse authenticate(AuthenticationRequest request);
}

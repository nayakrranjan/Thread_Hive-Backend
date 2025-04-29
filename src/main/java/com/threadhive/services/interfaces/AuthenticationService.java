package com.threadhive.services.interfaces;

import com.threadhive.dtos.request.AuthenticationRequest;
import com.threadhive.dtos.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
}

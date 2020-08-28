package com.netcracker.edu.distancestudyweb.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationService {
    Authentication authenticate(Authentication request) throws AuthenticationException;
}

package com.netcracker.edu.distancestudyweb.service.impl.auth;

import com.netcracker.edu.distancestudyweb.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

//    @Autowired
    private AuthenticationManager authManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return authManager.authenticate(authentication);
    }

}

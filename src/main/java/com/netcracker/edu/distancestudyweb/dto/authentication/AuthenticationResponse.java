package com.netcracker.edu.distancestudyweb.dto.authentication;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthenticationResponse {
    private String jwtToken;
}

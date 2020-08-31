package com.netcracker.edu.distancestudyweb.dto.authentication;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AuthenticationResponse {
    private String jwtToken;
}

package com.netcracker.edu.distancestudyweb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@AllArgsConstructor
public class ClientPrincipal {
    private String token;
    private String email;
    private List<GrantedAuthority> authorities;
}

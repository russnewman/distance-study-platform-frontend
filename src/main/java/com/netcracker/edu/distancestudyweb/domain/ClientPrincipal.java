package com.netcracker.edu.distancestudyweb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ClientPrincipal {
    private String token;
    private String email;
    private Role role;
    private List<GrantedAuthority> authorities;

    public boolean isAdmin() {
        return role.equals(Role.ROLE_ADMIN);
    }

    public boolean isStudent() {
        return role.equals(Role.ROLE_STUDENT);
    }

    public boolean isTeacher() {
        return role.equals(Role.ROLE_TEACHER);
    }
}

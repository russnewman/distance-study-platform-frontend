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
    private User user;

    public boolean isAdmin() {
        return user.getRole().equals(Role.ROLE_ADMIN);
    }

    public boolean isStudent() {
        return user.getRole().equals(Role.ROLE_STUDENT);
    }

    public boolean isTeacher() {
        return user.getRole().equals(Role.ROLE_TEACHER);
    }
}

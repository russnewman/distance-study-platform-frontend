package com.netcracker.edu.distancestudyweb.service.impl;

import com.netcracker.edu.distancestudyweb.domain.ClientPrincipal;
import com.netcracker.edu.distancestudyweb.domain.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static String getEmail() {
        return getPrincipal().getEmail();
    }

    public static boolean isAdmin() {
        return getPrincipal().isAdmin();
    }

    public static boolean isStudent() {
        return getPrincipal().isStudent();
    }

    public static boolean isTeacher() {
        return getPrincipal().isTeacher();
    }

    public static ClientPrincipal getPrincipal() {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new IllegalStateException("User is not authenticated");
        }
        return (ClientPrincipal) auth.getPrincipal();
    }

    public static Role getRole() {
        return getPrincipal().getRole();
    }
}

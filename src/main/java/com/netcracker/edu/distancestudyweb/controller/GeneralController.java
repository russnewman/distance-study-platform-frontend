package com.netcracker.edu.distancestudyweb.controller;

import com.netcracker.edu.distancestudyweb.domain.Role;
import com.netcracker.edu.distancestudyweb.service.impl.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController {
    @GetMapping(value = "/home")
    public String getHome() {
        String view;
        Role role = SecurityUtils.getRole();
        switch (SecurityUtils.getRole()) {
            case ROLE_STUDENT:
                view = "studentHome";
                break;
            case ROLE_ADMIN:
                view =  "adminHome";
                break;
            case ROLE_TEACHER:
                view = "teacherHome";
                break;
            default:
                throw new IllegalArgumentException("Not supported role: " + role.name());
        }
        return view;
    }
}

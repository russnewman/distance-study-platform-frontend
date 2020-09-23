package com.netcracker.edu.distancestudyweb.controller;

import com.netcracker.edu.distancestudyweb.domain.Role;
import com.netcracker.edu.distancestudyweb.service.impl.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GeneralController {
    @GetMapping(value = "/home")
    public String getHome() {
        String view;
        Role role = SecurityUtils.getRole();
        view = switch (SecurityUtils.getRole()) {
            case ROLE_STUDENT -> "studentHome";
            case ROLE_ADMIN -> "adminHome";
            case ROLE_TEACHER -> "teacher_profile";
            default -> throw new IllegalArgumentException("Not supported role: " + role.name());
        };
        return view;
    }


    @GetMapping(value = "teacherProfile/{teacherId}")
    public String teacherProfile(@PathVariable Long teacherId, Model model){
        model.addAttribute("teacherId",teacherId);
        return "teacher_profile";
    }

    @GetMapping(value = "/test")
    public String getTest() {
        return "testUploadFile";
    }
}

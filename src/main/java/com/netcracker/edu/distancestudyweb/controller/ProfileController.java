package com.netcracker.edu.distancestudyweb.controller;

import com.netcracker.edu.distancestudyweb.domain.User;
import com.netcracker.edu.distancestudyweb.dto.user.ChangePasswordRequest;
import com.netcracker.edu.distancestudyweb.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getProfile(Model model) {
        User user = userService.getUserInfo();
        model.addAttribute("user", user);
        model.addAttribute("passwordTab", false);
        return "studentProfile";
    }
}

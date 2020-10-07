package com.netcracker.edu.distancestudyweb.controller;

import com.netcracker.edu.distancestudyweb.domain.User;
import com.netcracker.edu.distancestudyweb.dto.user.ChangePasswordRequest;
import com.netcracker.edu.distancestudyweb.exception.DifferentPasswordsException;
import com.netcracker.edu.distancestudyweb.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.netcracker.edu.distancestudyweb.controller.ControllerUtils.ERROR;
import static com.netcracker.edu.distancestudyweb.controller.ControllerUtils.SERVICE_ERROR_MESSAGE;
import static com.netcracker.edu.distancestudyweb.controller.ControllerUtils.SUCCESS;

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
        return "userProfile";
    }

    @PostMapping
    public String changePassword(ChangePasswordRequest request, Model model) {
        try {
            userService.changePassword(request);
            model.addAttribute(SUCCESS, "Password has been changed");
        } catch (DifferentPasswordsException e) {
            model.addAttribute(ERROR, "Old password is not correct");
        } catch (Exception e) {
            model.addAttribute(ERROR, SERVICE_ERROR_MESSAGE);
        }
        model.addAttribute("user", userService.getUserInfo());
        model.addAttribute("passwordTab", true);
        return "userProfile";
    }
}

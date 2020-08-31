package com.netcracker.edu.distancestudyweb.controller;

import com.netcracker.edu.distancestudyweb.dto.authentication.AuthenticationRequest;
import com.netcracker.edu.distancestudyweb.service.AuthenticationService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.netcracker.edu.distancestudyweb.controller.ControllerUtils.SERVICE_ERROR_MESSAGE;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationService authService;

    public AuthController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping
    public String authenticate(AuthenticationRequest authRequest, Model model) {
        String username = authRequest.getEmail();
        String viewName = null;
        try {
            Authentication response = authService.authenticate(new UsernamePasswordAuthenticationToken(username, authRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(response);
            return "redirect:home";
        } catch (final BadCredentialsException ex) {
            viewName = handleException(model, "Bad credentials");
        } catch (Exception e) {
            viewName = handleException(model, SERVICE_ERROR_MESSAGE);
        }
        return viewName;
    }

    @GetMapping
    public String authenticate(Model model) {
        return "auth";
    }

    private String handleException(Model model, String errorMessage) {
        model.addAttribute("message", errorMessage);
        model.addAttribute("messageType", "danger");
        return "auth";
    }

}

package com.netcracker.edu.distancestudyweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/homework")
public class HomeworkController {
    @GetMapping
    public String getHomework() {
        return "homework";
    }
}

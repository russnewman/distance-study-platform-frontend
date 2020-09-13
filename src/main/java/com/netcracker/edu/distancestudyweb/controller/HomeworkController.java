package com.netcracker.edu.distancestudyweb.controller;

import com.netcracker.edu.distancestudyweb.domain.FileInfo;
import com.netcracker.edu.distancestudyweb.domain.Role;
import com.netcracker.edu.distancestudyweb.domain.StudentEvent;
import com.netcracker.edu.distancestudyweb.dto.homework.AssignmentFormRequest;
import com.netcracker.edu.distancestudyweb.dto.homework.AssignmentRequestDto;
import com.netcracker.edu.distancestudyweb.dto.homework.EventFormRequest;
import com.netcracker.edu.distancestudyweb.service.HomeworkService;
import com.netcracker.edu.distancestudyweb.service.impl.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/homework")
public class HomeworkController {
    private HomeworkService homeworkService;
    private @Value("${server.url}") String url;

    public HomeworkController(HomeworkService homeworkService) {
        this.homeworkService = homeworkService;
    }

    @GetMapping
    public String getHomework(Model model, EventFormRequest formRequest) {
        List<StudentEvent> studentEvents = homeworkService.getEvents(formRequest);
        model.addAttribute("events", studentEvents);
        model.addAttribute("serverUrl", url);
        return "studentHomework";
    }

    @PostMapping
    public String uploadHomework(AssignmentFormRequest formRequest) {
        homeworkService.uploadHomework(formRequest);
        return "redirect:/homework";
    }

    private String chooseView(Role role) {
        String view;
        switch (role) {
            case ROLE_STUDENT:
                view = "studentHomework";
                break;
            case ROLE_TEACHER:
                view = "teacherHomework";
                break;
            default:
                throw new IllegalArgumentException("Not supported role: " + role.name());
        }
        return view;
    }
}

package com.netcracker.edu.distancestudyweb.controller;

import com.netcracker.edu.distancestudyweb.domain.StudentEvent;
import com.netcracker.edu.distancestudyweb.domain.Subject;
import com.netcracker.edu.distancestudyweb.dto.homework.AssignmentFormRequest;
import com.netcracker.edu.distancestudyweb.dto.homework.EventFormRequest;
import com.netcracker.edu.distancestudyweb.service.HomeworkService;
import com.netcracker.edu.distancestudyweb.service.SubjectService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/studentHomework")
public class StudentHomeworkController {
    private HomeworkService homeworkService;
    private SubjectService subjectService;
    private @Value("${server.url}") String url;

    public StudentHomeworkController(HomeworkService homeworkService, SubjectService subjectService) {
        this.homeworkService = homeworkService;
        this.subjectService = subjectService;
    }

    @GetMapping
    public String getHomework(Model model, EventFormRequest formRequest) {
        List<StudentEvent> studentEvents = homeworkService.getEvents(formRequest);
        studentEvents.forEach(event -> event.setElapsed(event.getEndDate().isBefore(LocalDateTime.now())));
        List<Subject> subjects = subjectService.getAll();
        subjects.stream()
                .filter(subject -> subject.getId().equals(formRequest.getSubjectId()))
                .findFirst().ifPresent(value -> value.setSelected(true));
        model.addAttribute("events", studentEvents);
        model.addAttribute("subjects", subjects);
        model.addAttribute("serverUrl", url);
        return "studentHomework";
    }

    @PostMapping
    public String uploadHomework(AssignmentFormRequest formRequest) {
        homeworkService.uploadHomework(formRequest);
        return "redirect:/studentHomework";
    }
}

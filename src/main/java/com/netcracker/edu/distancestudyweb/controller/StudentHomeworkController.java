package com.netcracker.edu.distancestudyweb.controller;

import com.netcracker.edu.distancestudyweb.domain.StudentEvent;
import com.netcracker.edu.distancestudyweb.domain.Subject;
import com.netcracker.edu.distancestudyweb.dto.GetStudentEventsResponseDto;
import com.netcracker.edu.distancestudyweb.dto.SubjectDto;
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
import java.util.ArrayList;
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
        try {
            GetStudentEventsResponseDto response = homeworkService.getEvents(formRequest);
            List<StudentEvent> studentEvents = response.getEvents();
            studentEvents.forEach(event -> event.setElapsed(event.getEndDate().isBefore(LocalDateTime.now())));
            List<Subject> subjects = subjectService.getAll();
            subjects.stream()
                    .filter(subject -> subject.getId().equals(formRequest.getSubjectId()))
                    .findFirst().ifPresent(value -> value.setSelected(true));
            model.addAttribute("events", studentEvents);
            model.addAttribute("subjects", subjects);
            model.addAttribute("serverUrl", url);
            model.addAttribute("pageCount", response.getPageCount());
            model.addAttribute("activePage", formRequest.getPage());
            model.addAttribute("subjectId", formRequest.getSubjectId());
            return "studentHomework";
        } catch (Exception e) {
            return "error";
        }
    }


    @PostMapping
    public String uploadHomework(AssignmentFormRequest formRequest) {
        try {
            homeworkService.uploadHomework(formRequest);
            return "redirect:/studentHomework";
        } catch (Exception e) {
            return "error";
        }
    }
}

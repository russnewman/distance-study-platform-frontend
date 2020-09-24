package com.netcracker.edu.distancestudyweb.controller;

import com.netcracker.edu.distancestudyweb.service.ScheduleService;
import com.netcracker.edu.distancestudyweb.service.SubjectService;
import com.netcracker.edu.distancestudyweb.service.impl.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Slf4j
@PreAuthorize("isAuthenticated()")
@RequestMapping("/studentSchedule")
public class StudentTimetableController {
    private final ScheduleService scheduleUiService;
    private final SubjectService subjectUiService;


    @Autowired
    public StudentTimetableController(ScheduleService scheduleUiService, SubjectService subjectUiService) {
        this.scheduleUiService = scheduleUiService;
        this.subjectUiService = subjectUiService;
    }

    @GetMapping("/full")
    public String getSchedule(
            Model model){
        Long studentId = SecurityUtils.getId();
        model.addAttribute("schedules", scheduleUiService.getStudentFullSchedule(studentId));
        model.addAttribute("subjects", subjectUiService.getAllSubjects());
        model.addAttribute("todaySchedules", scheduleUiService.getStudentTodaySchedule(studentId));
        model.addAttribute("tomorrowSchedules", scheduleUiService.getStudentTomorrowSchedule(studentId));
        return "student_schedule";
    }

    @GetMapping("/subjectSchedule")
    public String getSubjectSchedule(@RequestParam("subject") Long subjectId, Model model){
        Long studentId = SecurityUtils.getId();
        model.addAttribute("schedules", scheduleUiService.getStudentSubjectSchedule(studentId, subjectId));
        model.addAttribute("subjects", subjectUiService.getAllSubjects());
        return "student_schedule";
    }

    @GetMapping("/test_page")
    public String test(Model model,
                       @RequestParam(name="name", required=false, defaultValue="World") String name){
        model.addAttribute("name", name);
        return "test_page";
    }


}

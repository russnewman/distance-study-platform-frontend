package com.netcracker.edu.distancestudyweb.controller;

import com.netcracker.edu.distancestudyweb.dto.ScheduleDto;
import com.netcracker.edu.distancestudyweb.dto.ScheduleVDto;
import com.netcracker.edu.distancestudyweb.dto.StudentScheduleDto;
import com.netcracker.edu.distancestudyweb.service.ScheduleService;
import com.netcracker.edu.distancestudyweb.service.SubjectService;
import com.netcracker.edu.distancestudyweb.service.impl.ScheduleServiceImpl;
import com.netcracker.edu.distancestudyweb.service.impl.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;


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
        model.addAttribute("subjects", subjectUiService.getAllSubjects());
        model.addAttribute("mappedSchedule", scheduleUiService.getStudentFullSchedule(studentId));
        model.addAttribute("todaySchedule", scheduleUiService.getStudentTodaySchedule(studentId));
        model.addAttribute("tomorrowSchedule", scheduleUiService.getStudentTomorrowSchedule(studentId));
        return "student_schedule";
    }

    @GetMapping("/subjectSchedule")
    public String getSubjectSchedule(@RequestParam("subject") Long subjectId, Model model){
        Long studentId = SecurityUtils.getId();
        if(subjectId == 0)  model.addAttribute("mappedSchedule", scheduleUiService.getStudentFullSchedule(studentId));
        else model.addAttribute("mappedSchedule", scheduleUiService.getStudentSubjectSchedule(studentId, subjectId));
        model.addAttribute("subjects", subjectUiService.getAllSubjects());
        model.addAttribute("todaySchedule", scheduleUiService.getStudentTodaySchedule(studentId));
        model.addAttribute("tomorrowSchedule", scheduleUiService.getStudentTomorrowSchedule(studentId));
        return "student_schedule";
    }
}

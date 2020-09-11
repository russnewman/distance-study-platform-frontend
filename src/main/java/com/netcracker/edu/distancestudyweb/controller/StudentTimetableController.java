package com.netcracker.edu.distancestudyweb.controller;

import com.netcracker.edu.distancestudyweb.service.ScheduleService;
import com.netcracker.edu.distancestudyweb.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Slf4j
@RequestMapping("/studentSchedule")
public class StudentTimetableController {
    private final ScheduleService scheduleUiService;
    private final SubjectService subjectUiService;


    @Autowired
    public StudentTimetableController(ScheduleService scheduleUiService, SubjectService subjectUiService) {
        this.scheduleUiService = scheduleUiService;
        this.subjectUiService = subjectUiService;
    }

    @GetMapping("/full/{studentId}")
    public String getSchedule(
            @PathVariable("studentId") Long studentId,
            Model model){
        model.addAttribute("schedules", scheduleUiService.getStudentFullSchedule(studentId).getSchedules());
        model.addAttribute("subjects", subjectUiService.getAllSubjects().getSubjects());
        model.addAttribute("todaySchedules", scheduleUiService.getStudentTodaySchedule(studentId).getSchedules());
        model.addAttribute("tomorrowSchedules", scheduleUiService.getStudentTomorrowSchedule(studentId).getSchedules());
        //model.addAttribute("currentSubject", scheduleUiService.getStudentCurrentSubject(studentId));
        //model.addAttribute("nextSubject", scheduleUiService.getStudentNextSubject(studentId));
        return "student_schedule";
    }

    @GetMapping("/subjectSchedule/{studentId}")
    public String getSubjectSchedule(@PathVariable("studentId") Long studentId, @RequestParam("subject") Long subjectId, Model model){
        model.addAttribute("schedules", scheduleUiService.getStudentSubjectSchedule(studentId, subjectId).getSchedules());
        model.addAttribute("subjects", subjectUiService.getAllSubjects().getSubjects());
        return "student_schedule";
    }

    @GetMapping("/test_page")
    public String test(Model model,
                       @RequestParam(name="name", required=false, defaultValue="World") String name){
        model.addAttribute("name", name);
        return "test_page";
    }


}

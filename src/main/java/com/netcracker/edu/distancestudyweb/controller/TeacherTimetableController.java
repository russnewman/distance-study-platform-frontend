package com.netcracker.edu.distancestudyweb.controller;


import com.netcracker.edu.distancestudyweb.dto.GroupDto;
import com.netcracker.edu.distancestudyweb.dto.ScheduleDto;
import com.netcracker.edu.distancestudyweb.service.ScheduleService;
import com.netcracker.edu.distancestudyweb.service.SubjectService;
import com.netcracker.edu.distancestudyweb.service.impl.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.*;

@Controller
public class TeacherTimetableController {

    private final ScheduleService scheduleUiService;
    private final SubjectService subjectUiService;
    private @Value("${server.url}") String uiUrl;



    @Autowired
    public TeacherTimetableController(ScheduleService scheduleUiService, SubjectService subjectUiService) {
        this.scheduleUiService = scheduleUiService;
        this.subjectUiService = subjectUiService;
    }


    @GetMapping("/teacherSchedule")
    public String getWeekSchedule(
            @RequestParam(value = "weekIsOdd", required = false) Optional<Boolean> weekIsOddOptional,
            @RequestParam(value = "subjectId", required = false) Optional<Long> subjectIdOptional, Model model)
        {
            final Long teacherId = SecurityUtils.getId();

            model.addAttribute("subjects", subjectUiService.getSubjectsByTeacherId(teacherId));
            model.addAttribute("teacherId",teacherId);


            weekIsOddOptional.ifPresent(aBoolean -> model.addAttribute("weekIsOdd", aBoolean));

            List<ScheduleDto> weekSchedule = scheduleUiService.getTeacherSchedule(teacherId, weekIsOddOptional);
            List<ScheduleDto> tomorrowSchedule = scheduleUiService.getTomorrowTeacherSchedule(teacherId, weekIsOddOptional);

            ifSubjectPresent(subjectIdOptional, model, weekSchedule, tomorrowSchedule);
            return "teacher_schedule";
    }


    private void ifSubjectPresent(@RequestParam(value = "subject", required = false) Optional<Long> subjectIdOptional,
                                  Model model, List<ScheduleDto> weekSchedule, List<ScheduleDto> tomorrowSchedule) {

        Map<ScheduleDto, List<GroupDto>> weekScheduleMap ;
        Map<ScheduleDto, List<GroupDto>> tomorrowScheduleMap;

        if (subjectIdOptional.isPresent()) {

            Long subjectId = subjectIdOptional.get();
            model.addAttribute("subjectId", subjectId);
            weekScheduleMap = scheduleUiService.mapScheduleToGroups(scheduleUiService.getSubjectTeacherSchedule(weekSchedule, subjectId));
            tomorrowScheduleMap = scheduleUiService.mapScheduleToGroups(scheduleUiService.getSubjectTeacherSchedule(tomorrowSchedule, subjectId));

        }
        else{
            weekScheduleMap = scheduleUiService.mapScheduleToGroups(weekSchedule);
            tomorrowScheduleMap = scheduleUiService.mapScheduleToGroups(tomorrowSchedule);
        }

        List<List<ScheduleDto>> weekSchedulesByDays = scheduleUiService.schedulesByDays(weekScheduleMap);
        List<List<ScheduleDto>> tomorrowSchedulesByDays = scheduleUiService.schedulesByDays(tomorrowScheduleMap);


        model.addAttribute("todayDayName", scheduleUiService.getTodayDayName());
        model.addAttribute("weekScheduleMap", weekScheduleMap);
        model.addAttribute("weekSchedulesByDays", weekSchedulesByDays);
        model.addAttribute("tomorrowScheduleMap", tomorrowScheduleMap);
        model.addAttribute("tomorrowSchedulesByDays", tomorrowSchedulesByDays);

    }


    @PostMapping("/updateLessonLink")
    public String updateLessonLink(
            @RequestParam Long scheduleId,
            @RequestParam String lessonLink,
            @RequestParam Boolean weekIsOdd,
            @RequestParam Long subjectId){
        scheduleUiService.updateLessonLink(scheduleId, lessonLink);


        String url = uiUrl + "/teacherSchedule";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("weekIsOdd", weekIsOdd)
                .queryParam("subjectId", subjectId);

        return "redirect:" + builder.toUriString();
    }

}

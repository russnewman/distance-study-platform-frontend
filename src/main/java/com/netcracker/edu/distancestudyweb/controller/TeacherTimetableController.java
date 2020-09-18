package com.netcracker.edu.distancestudyweb.controller;


import com.netcracker.edu.distancestudyweb.dto.GroupDto;
import com.netcracker.edu.distancestudyweb.dto.ScheduleDto;
import com.netcracker.edu.distancestudyweb.service.ScheduleService;
import com.netcracker.edu.distancestudyweb.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class TeacherTimetableController {

    private final ScheduleService scheduleUiService;
    private final SubjectService subjectUiService;


    @Autowired
    public TeacherTimetableController(ScheduleService scheduleUiService, SubjectService subjectUiService) {
        this.scheduleUiService = scheduleUiService;
        this.subjectUiService = subjectUiService;
    }


    @GetMapping("/teacherSchedule/{teacherId}")
    public String getWeekSchedule(
            @PathVariable("teacherId") Long teacherId,
            @RequestParam(value = "weekIsOdd", required = false) Optional<Boolean> weekIsOddOptional,
            @RequestParam(value = "subjectId", required = false) Optional<Long> subjectIdOptional, Model model)
        {

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

        List<ScheduleDto> weekScheduleMapKeys = scheduleUiService.mapKeysList(weekScheduleMap);
        List<ScheduleDto> tomorrowScheduleMapKeys = scheduleUiService.mapKeysList(tomorrowScheduleMap);

        model.addAttribute("weekScheduleMap", weekScheduleMap);
        model.addAttribute("weekScheduleMapKeys", weekScheduleMapKeys);
        model.addAttribute("tomorrowScheduleMap", tomorrowScheduleMap);
        model.addAttribute("tomorrowScheduleMapKeys", tomorrowScheduleMapKeys);

    }
}

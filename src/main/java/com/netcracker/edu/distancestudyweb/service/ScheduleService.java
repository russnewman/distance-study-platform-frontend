package com.netcracker.edu.distancestudyweb.service;


import com.netcracker.edu.distancestudyweb.dto.GroupDto;
import com.netcracker.edu.distancestudyweb.dto.ScheduleDto;
import com.netcracker.edu.distancestudyweb.dto.SubjectDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface ScheduleService {
    List<ScheduleDto> getStudentFullSchedule(Long studentId);


//    List<ScheduleDto> getTeacherSchedule(Long teacherId);
//    List<ScheduleDto> getTomorrowTeacherSchedule(Long teacherId);

    List<ScheduleDto> getTeacherSchedule(Long teacherId, Optional<Boolean> weekIsOdd);
    List<ScheduleDto> getTomorrowTeacherSchedule(Long teacherId, Optional<Boolean> weekIsOdd);

    List<ScheduleDto> getSubjectTeacherSchedule(List<ScheduleDto> list, Long subjectId);
    Map<ScheduleDto, List<GroupDto>> mapScheduleToGroups(List<ScheduleDto> list);
    List<ScheduleDto> mapKeysList(Map<ScheduleDto, List<GroupDto>> map);





    List<ScheduleDto> getStudentTodaySchedule(Long studentId);
    List<ScheduleDto> getStudentTomorrowSchedule(Long studentId);
    SubjectDto getStudentCurrentSubject(Long studentId);
    SubjectDto getStudentNextSubject(Long studentId);
    List<ScheduleDto> getStudentSubjectSchedule(Long studentId, Long subjectId);
}

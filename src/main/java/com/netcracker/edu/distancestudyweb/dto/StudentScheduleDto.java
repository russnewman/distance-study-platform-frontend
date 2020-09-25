package com.netcracker.edu.distancestudyweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentScheduleDto {
    private Long id;
    private SubjectDto subjectDto;
    private TeacherDto teacher;
    private String classType;
    private GroupDto groupDto;
    private String dayName;
    private ClassTimeDto classTimeDto;
    private Boolean weekIsOdd;
}

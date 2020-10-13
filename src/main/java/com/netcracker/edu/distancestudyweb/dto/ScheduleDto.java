package com.netcracker.edu.distancestudyweb.dto;

import lombok.*;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class  ScheduleDto {
    private Long id;

    @EqualsAndHashCode.Include
    private SubjectDto subjectDto;

    @EqualsAndHashCode.Include
    private TeacherDto teacher;
    private String classType;
    private GroupDto groupDto;

    @EqualsAndHashCode.Include
    private String dayName;

    @EqualsAndHashCode.Include
    private ClassTimeDto classTimeDto;

    @EqualsAndHashCode.Include
    private Boolean weekIsOdd;

    private String lessonLink;


}

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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ScheduleDto schedule = (ScheduleDto) o;
//        return teacher.equals(schedule.getTeacher()) &&
//                classTimeDto.getStartTime().equals(schedule.classTimeDto.getStartTime()) &&
//                dayName.equals(schedule.dayName) &&
//                weekIsOdd.equals(schedule.weekIsOdd) &&
//                subjectDto.equals(schedule.getSubjectDto());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(teacher, classTimeDto.getStartTime(), dayName, weekIsOdd, subjectDto);
//    }

}

package com.netcracker.edu.distancestudyweb.dto;

import lombok.Data;
import java.util.List;


@Data
public class StudentDto {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String login;
    private List<ScheduleDto> schedules;
}
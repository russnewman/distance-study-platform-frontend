package com.netcracker.edu.distancestudyweb.dto;

import lombok.Data;

@Data
public class AssignmentDto {
    private Long id;
    private Integer grade;
    private EventDto event;
    private StudentDto student;
    private DatabaseFileDto dbFile;
    private String studentCommentary;
    private String teacherCommentary;
}
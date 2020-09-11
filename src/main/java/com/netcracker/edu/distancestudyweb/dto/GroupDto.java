package com.netcracker.edu.distancestudyweb.dto;
import lombok.Data;

@Data

public class GroupDto {
    private Long id;
    private String groupName;
    private FacultyDto faculty;
}

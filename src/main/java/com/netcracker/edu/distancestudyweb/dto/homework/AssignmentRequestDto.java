package com.netcracker.edu.distancestudyweb.dto.homework;

import lombok.Data;

@Data
public class AssignmentRequestDto {
    private Long studentId;
    private String commentary;
    private DatabaseFileDto dbFileDto;
}

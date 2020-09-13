package com.netcracker.edu.distancestudyweb.dto.homework;

import com.netcracker.edu.distancestudyweb.domain.FileInfo;
import lombok.Data;

@Data
public class AssignmentRequestDto {
    private Long studentId;
    private String description;
    private FileInfo fileInfo;
}

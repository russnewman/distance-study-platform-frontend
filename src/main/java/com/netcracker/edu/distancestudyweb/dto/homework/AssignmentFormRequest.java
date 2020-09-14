package com.netcracker.edu.distancestudyweb.dto.homework;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AssignmentFormRequest {
    private Long eventId;
    private String commentary;
    private MultipartFile file;
}

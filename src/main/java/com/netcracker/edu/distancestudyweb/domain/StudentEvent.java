package com.netcracker.edu.distancestudyweb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentEvent {
    private Long id;
    private String teacher;
    private String subject;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Assignment> assignments;
    private String description;
    private Long fileId;
}

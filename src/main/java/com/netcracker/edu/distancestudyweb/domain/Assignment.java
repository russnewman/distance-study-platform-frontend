package com.netcracker.edu.distancestudyweb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {
    private Long id;
    private int grade;
    private String fileId;
    private String commentary;
}

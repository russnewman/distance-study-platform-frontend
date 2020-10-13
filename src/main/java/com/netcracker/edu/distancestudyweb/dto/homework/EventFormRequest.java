package com.netcracker.edu.distancestudyweb.dto.homework;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventFormRequest {
    private Long subjectId;
    private Integer page;
}

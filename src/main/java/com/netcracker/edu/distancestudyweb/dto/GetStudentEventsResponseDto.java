package com.netcracker.edu.distancestudyweb.dto;

import com.netcracker.edu.distancestudyweb.domain.StudentEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetStudentEventsResponseDto {
    private List<StudentEvent> events;
    private int pageCount;
}

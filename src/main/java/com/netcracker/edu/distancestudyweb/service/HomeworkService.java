package com.netcracker.edu.distancestudyweb.service;

import com.netcracker.edu.distancestudyweb.domain.StudentEvent;
import com.netcracker.edu.distancestudyweb.dto.GetStudentEventsResponseDto;
import com.netcracker.edu.distancestudyweb.dto.homework.AssignmentFormRequest;
import com.netcracker.edu.distancestudyweb.dto.homework.EventFormRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HomeworkService {
    GetStudentEventsResponseDto getEvents(EventFormRequest formRequest);
    void uploadHomework(AssignmentFormRequest formRequest);
    ResponseEntity<Resource> downloadFile(String fileId);
}

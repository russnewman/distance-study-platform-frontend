package com.netcracker.edu.distancestudyweb.service;

import com.netcracker.edu.distancestudyweb.domain.StudentEvent;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HomeworkService {
    List<StudentEvent> getEvents();
    void uploadHomework(MultipartFile file, String eventId);
}

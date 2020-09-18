package com.netcracker.edu.distancestudyweb.service;

import com.netcracker.edu.distancestudyweb.dto.StudentDto;
import java.util.List;


public interface StudentService {
    StudentDto getStudentByEmail(String email);
    StudentDto getStudent(Long id);
    List<StudentDto> getStudentsByGroup(Long groupId);
}

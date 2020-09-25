package com.netcracker.edu.distancestudyweb.service;



import com.netcracker.edu.distancestudyweb.dto.AssignmentDto;

import java.util.List;

public interface AssignmentService {
    List<AssignmentDto> getAllStudentAssignments(Long studentId);
    List<AssignmentDto> getAllStudentSubjectAssignments(Long studentId, Long subjectId);
    List<AssignmentDto> getAssessedStudentAssignments(Long studentId);
    List<AssignmentDto> getUnassessedStudentAssignments(Long studentId);
    List<AssignmentDto> getActiveStudentAssignments(Long studentId);
    List<AssignmentDto> getEventStudentAssignments(Long studentId, Long eventId);
    List<AssignmentDto> getEventStudentAssessedAssignments(Long studentId, Long eventId);
    List<AssignmentDto> getEventStudentUnassessedAssignments(Long studentId, Long eventId);
    void save(AssignmentDto assignment);


    void update(AssignmentDto assignment);
    List<List<AssignmentDto>> getAssignmentsByEvent(Long eventId, Long groupId);
//    List<List<AssignmentDto>> getAssessedStudentsAssignments(Long eventId);
//    List<AssignmentDto> getUnassessedStudentsAssignments(Long eventId);
}


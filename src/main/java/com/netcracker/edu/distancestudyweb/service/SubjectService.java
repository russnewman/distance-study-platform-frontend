package com.netcracker.edu.distancestudyweb.service;


import com.netcracker.edu.distancestudyweb.domain.Subject;
import com.netcracker.edu.distancestudyweb.dto.SubjectDto;

import java.util.List;


public interface SubjectService {
    List<Subject> getAll();
    List<SubjectDto> getAllSubjects();

    List<SubjectDto> getSubjectsByTeacherId(Long teacherId);
}

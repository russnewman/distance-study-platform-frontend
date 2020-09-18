package com.netcracker.edu.distancestudyweb.service;

import com.netcracker.edu.distancestudyweb.domain.Subject;
import com.netcracker.edu.distancestudyweb.dto.SubjectDto;
import com.netcracker.edu.distancestudyweb.dto.SubjectDtoList;

import java.util.List;
import java.util.List;

public interface SubjectService {
    List<Subject> getAll();

    SubjectDtoList getAllSubjects();

    List<SubjectDto> getSubjectsByTeacherId(Long teacherId);
}

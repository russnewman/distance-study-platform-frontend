package com.netcracker.edu.distancestudyweb.service;

import com.netcracker.edu.distancestudyweb.dto.GroupDto;


import java.util.List;

public interface GroupService {
    public List<GroupDto> findGroupsByTeacherAndSubject(Long teacherId, String subjectName);
}

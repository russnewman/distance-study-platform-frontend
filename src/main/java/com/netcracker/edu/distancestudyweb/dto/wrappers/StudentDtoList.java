package com.netcracker.edu.distancestudyweb.dto.wrappers;


import com.netcracker.edu.distancestudyweb.dto.StudentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class StudentDtoList{
    private List<StudentDto> students;
}

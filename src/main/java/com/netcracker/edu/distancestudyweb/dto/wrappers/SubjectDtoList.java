package com.netcracker.edu.distancestudyweb.dto.wrappers;

import com.netcracker.edu.distancestudyweb.dto.SubjectDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SubjectDtoList {
    List<SubjectDto> subjects;
}

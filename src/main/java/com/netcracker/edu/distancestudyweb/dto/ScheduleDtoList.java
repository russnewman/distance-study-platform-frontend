package com.netcracker.edu.distancestudyweb.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ScheduleDtoList implements Serializable {
    private List<ScheduleDto> schedules;
}

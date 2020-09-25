package com.netcracker.edu.distancestudyweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleVDto {
    String dayName;
    ClassTimeDto classTimeDto;
    String subject;
    String teacher;
    Boolean weekIsOdd;
}
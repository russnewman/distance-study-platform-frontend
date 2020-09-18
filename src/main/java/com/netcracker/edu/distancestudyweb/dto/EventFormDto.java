package com.netcracker.edu.distancestudyweb.dto;

import lombok.Data;
import java.util.Date;

@Data
public class EventFormDto {
    private Long teacherId;
    private String subjectName;
    private String groupName;
    private String description;
    private Date startTime;
    private Date endTime;
    private DatabaseFileDto databaseFileDto;

}

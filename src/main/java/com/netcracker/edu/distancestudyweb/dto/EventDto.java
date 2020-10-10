package com.netcracker.edu.distancestudyweb.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto implements Serializable {

    private Long id;
    private TeacherDto teacher;
    private SubjectDto subject;
    private GroupDto group;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    private DatabaseFileDto databaseFileDto;

    private Boolean canDeleteEvent;
    /**
     * Status true means that all assignments of this event are verified(have a grade)
     * while status false means at least one assignment is not verified
     * */
    private Boolean status;

}

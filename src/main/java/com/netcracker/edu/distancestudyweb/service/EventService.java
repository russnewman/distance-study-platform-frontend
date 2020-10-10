package com.netcracker.edu.distancestudyweb.service;

import com.netcracker.edu.distancestudyweb.dto.EventDto;
import com.netcracker.edu.distancestudyweb.dto.EventFormDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;


public interface EventService {
    void saveEventDto(EventFormDto eventFormDto);
    void editEvent(Long eventId, EventFormDto eventFormDto);

    Page<EventDto> getEvents(Long teacherId, String sortingType, String subjectName, Integer pageNumber);
//    List<EventDto> getEvents(Long teacherId, String sortingType, String subjectName);


    void deleteEvent(Long eventId);
    EventDto getEventById(Long eventId);
    Boolean canDeleteEvent(Long eventId);
}

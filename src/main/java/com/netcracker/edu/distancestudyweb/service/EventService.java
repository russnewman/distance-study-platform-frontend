package com.netcracker.edu.distancestudyweb.service;

import com.netcracker.edu.distancestudyweb.dto.EventDto;
import com.netcracker.edu.distancestudyweb.dto.EventFormDto;
import com.netcracker.edu.distancestudyweb.dto.wrappers.EventDtoList;

import java.util.List;
import java.util.Map;


public interface EventService {
    void saveEventDto(EventFormDto eventFormDto);
    void editEvent(Long eventId, EventFormDto eventFormDto);
    List<EventDto> getEvents(Long teacherId, String sortingType, String subjectName);
    void deleteEvent(Long eventId);
    EventDto getEventById(Long eventId);
    Boolean canDeleteEvent(Long eventId);
}

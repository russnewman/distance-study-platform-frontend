package com.netcracker.edu.distancestudyweb.service.impl;

import com.netcracker.edu.distancestudyweb.dto.EventDto;
import com.netcracker.edu.distancestudyweb.dto.EventFormDto;
import com.netcracker.edu.distancestudyweb.dto.wrappers.EventDtoList;
import com.netcracker.edu.distancestudyweb.service.EventService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class EventServiceImpl implements EventService {

    private String restURL = "http://localhost:8080";

    @Override
    public void saveEventDto(EventFormDto eventDto) {
        String URL = restURL + "/saveEvent";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EventFormDto> request = new HttpEntity<>(eventDto, headers);
        ResponseEntity<String> response
                = restTemplate.postForEntity(URL, request, String.class);
    }


    @Override
    public void editEvent(Long eventId, EventFormDto eventFormDto) {
        String URL = restURL + "/editEvent";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("eventId", eventId);
        HttpEntity<EventFormDto> request = new HttpEntity<>(eventFormDto, headers);
        ResponseEntity<String> response
                = restTemplate.postForEntity(builder.toUriString(), request, String.class);
    }


    @Override
    public List<EventDto> getEvents(Long teacherId, String sortingType, String subjectName) {

        String URL = restURL + "/getEvents";
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("teacherId", teacherId)
                .queryParam("sortingType", sortingType)
                .queryParam("subjectName", subjectName);


        ResponseEntity<EventDtoList> response
                = restTemplate.getForEntity(builder.toUriString(), EventDtoList.class);
        return response.getBody().getEvents();
    }



    @Override
    public void deleteEvent(Long eventId) {

        String URL = restURL + "/deleteEvent";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Long> request = new HttpEntity<>(eventId, headers);
        ResponseEntity<String> response
                = restTemplate.postForEntity(URL, request, String.class);
    }


    @Override
    public EventDto getEventById(Long eventId) {

        String URL = restURL + "/getEventById";
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("eventId", eventId);
        ResponseEntity<EventDto> response
                = restTemplate.getForEntity(builder.toUriString(), EventDto.class);
        return response.getBody();
    }



    @Override
    public Boolean canDeleteEvent(Long eventId) {
        String URL = restURL + "/canDeleteEvent";
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("eventId", eventId);
        ResponseEntity<Boolean> response
                = restTemplate.getForEntity(builder.toUriString(), Boolean.class);
        return response.getBody();
    }


}

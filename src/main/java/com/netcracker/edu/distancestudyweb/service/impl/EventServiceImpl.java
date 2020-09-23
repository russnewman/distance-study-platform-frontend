package com.netcracker.edu.distancestudyweb.service.impl;

import com.netcracker.edu.distancestudyweb.dto.EventDto;
import com.netcracker.edu.distancestudyweb.dto.EventFormDto;
import com.netcracker.edu.distancestudyweb.dto.ScheduleDto;
import com.netcracker.edu.distancestudyweb.dto.wrappers.EventDtoList;
import com.netcracker.edu.distancestudyweb.dto.wrappers.GroupDtoList;
import com.netcracker.edu.distancestudyweb.dto.wrappers.ScheduleDtoList;
import com.netcracker.edu.distancestudyweb.exception.InternalServiceException;
import com.netcracker.edu.distancestudyweb.service.EventService;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import com.netcracker.edu.distancestudyweb.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventServiceImpl implements EventService {


    private @Value("${rest.url}") String serverUrl;
    private RestTemplate restTemplate;
    private HttpEntityProvider entityProvider;

    public EventServiceImpl(RestTemplate restTemplate, HttpEntityProvider entityProvider) {
        this.restTemplate = restTemplate;
        this.entityProvider = entityProvider;
    }




    @Override
    public void saveEventDto(EventFormDto eventDto) {

        try{
            HttpEntity<EventFormDto> httpEntity = entityProvider.getDefaultWithTokenFromContext(eventDto, null);
            Map<String, Object> parameters = new HashMap<>();
            String url = ServiceUtils.injectParamsInUrl(serverUrl + "/saveEvent", parameters);
            restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        }
        catch (UnsupportedEncodingException e) {
            throw new InternalServiceException(e);
        }


//
//        String URL = restURL + "/saveEvent";
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<EventFormDto> request = new HttpEntity<>(eventDto, headers);
//        ResponseEntity<String> response
//                = restTemplate.postForEntity(URL, request, String.class);
    }


    @Override
    public void editEvent(Long eventId, EventFormDto eventFormDto) {

        try{
            HttpEntity<EventFormDto> httpEntity = entityProvider.getDefaultWithTokenFromContext(eventFormDto, null);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("eventId", eventId);
            String url = ServiceUtils.injectParamsInUrl(serverUrl + "/editEvent", parameters);
            restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        }
        catch (UnsupportedEncodingException e) {
            throw new InternalServiceException(e);
        }


//
//        String URL = restURL + "/editEvent";
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
//                .queryParam("eventId", eventId);
//        HttpEntity<EventFormDto> request = new HttpEntity<>(eventFormDto, headers);
//        ResponseEntity<String> response
//                = restTemplate.postForEntity(builder.toUriString(), request, String.class);
    }


    @Override
    public List<EventDto> getEvents(Long teacherId, String sortingType, String subjectName) {


        try{
            HttpEntity<?> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("teacherId", teacherId);
            parameters.put("sortingType", sortingType);
            parameters.put("subjectName", subjectName);
            String url = ServiceUtils.injectParamsInUrl(serverUrl + "/getEvents", parameters);
            ResponseEntity<EventDtoList> restAuthResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, EventDtoList.class);
            return restAuthResponse.getBody().getEvents();
        }
        catch (UnsupportedEncodingException e) {
            throw new InternalServiceException(e);
        }


//
//        String URL = restURL + "/getEvents";
//        RestTemplate restTemplate = new RestTemplate();
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
//                .queryParam("teacherId", teacherId)
//                .queryParam("sortingType", sortingType)
//                .queryParam("subjectName", subjectName);
//
//
//        ResponseEntity<EventDtoList> response
//                = restTemplate.getForEntity(builder.toUriString(), EventDtoList.class);
//        return response.getBody().getEvents();
    }



    @Override
    public void deleteEvent(Long eventId) {

        try{
            HttpEntity<?> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("eventId", eventId);
            String url = ServiceUtils.injectParamsInUrl(serverUrl + "/deleteEvent", parameters);
            restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        }
        catch (UnsupportedEncodingException e) {
            throw new InternalServiceException(e);
        }


//        String URL = restURL + "/deleteEvent";
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<Long> request = new HttpEntity<>(eventId, headers);
//        ResponseEntity<String> response
//                = restTemplate.postForEntity(URL, request, String.class);
    }


    @Override
    public EventDto getEventById(Long eventId) {

        try{
            HttpEntity<?> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("eventId", eventId);
            String url = ServiceUtils.injectParamsInUrl(serverUrl + "/getEventById", parameters);
            ResponseEntity<EventDto> restAuthResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, EventDto.class);
            return restAuthResponse.getBody();
        }
        catch (UnsupportedEncodingException e) {
            throw new InternalServiceException(e);
        }



//        String URL = restURL + "/getEventById";
//        RestTemplate restTemplate = new RestTemplate();
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
//                .queryParam("eventId", eventId);
//        ResponseEntity<EventDto> response
//                = restTemplate.getForEntity(builder.toUriString(), EventDto.class);
//        return response.getBody();
    }



    @Override
    public Boolean canDeleteEvent(Long eventId) {

        try{
            HttpEntity<?> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("eventId", eventId);
            String url = ServiceUtils.injectParamsInUrl(serverUrl + "/canDeleteEvent", parameters);
            ResponseEntity<Boolean> restAuthResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Boolean.class);
            return restAuthResponse.getBody();
        }
        catch (UnsupportedEncodingException e) {
            throw new InternalServiceException(e);
        }



//        String URL = restURL + "/canDeleteEvent";
//        RestTemplate restTemplate = new RestTemplate();
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
//                .queryParam("eventId", eventId);
//        ResponseEntity<Boolean> response
//                = restTemplate.getForEntity(builder.toUriString(), Boolean.class);
//        return response.getBody();
    }


}

package com.netcracker.edu.distancestudyweb.service.impl;

import com.netcracker.edu.distancestudyweb.dto.EventDto;
import com.netcracker.edu.distancestudyweb.dto.EventFormDto;
import com.netcracker.edu.distancestudyweb.exception.InternalServiceException;
import com.netcracker.edu.distancestudyweb.service.EventService;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import com.netcracker.edu.distancestudyweb.service.RestPageImpl;
import com.netcracker.edu.distancestudyweb.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
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
    }

    @Override
    public RestPageImpl<EventDto> getEvents(Long teacherId, String sortingType, String subjectName, Integer pageNumber) {

        try{
            HttpEntity<?> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("teacherId", teacherId);
            parameters.put("sortingType", sortingType);
            parameters.put("subjectName", subjectName);
            parameters.put("pageNumber", pageNumber);
            String url = ServiceUtils.injectParamsInUrl(serverUrl + "/getEvents", parameters);
            ResponseEntity<RestPageImpl<EventDto>> restAuthResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<RestPageImpl<EventDto>>(){});
            return restAuthResponse.getBody();
        }
        catch (UnsupportedEncodingException e) {
            throw new InternalServiceException(e);
        }
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
    }


}

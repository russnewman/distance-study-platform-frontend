package com.netcracker.edu.distancestudyweb.service.impl;

import com.netcracker.edu.distancestudyweb.domain.Assignment;
import com.netcracker.edu.distancestudyweb.domain.StudentEvent;
import com.netcracker.edu.distancestudyweb.service.HomeworkService;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static com.netcracker.edu.distancestudyweb.controller.ControllerUtils.URL_DELIMITER;

@Service
public class HomeworkServiceImpl implements HomeworkService {
    private @Value("${rest.url}") String serverUrl;
    private RestTemplate restTemplate;
    private HttpEntityProvider entityProvider;
    private List<StudentEvent> events;

    public HomeworkServiceImpl(RestTemplate restTemplate, HttpEntityProvider entityProvider) {
        this.restTemplate = restTemplate;
        this.entityProvider = entityProvider;
        initEvents();
    }

    private void initEvents() {

        StudentEvent event1 = new StudentEvent();
        event1.setTeacher("Teacher1");
        event1.setDescription("Description for 1 event");
        event1.setEndDate(LocalDateTime.now().plusMonths(1L));
        event1.setStartDate(LocalDateTime.now());
        event1.setId(1L);
        event1.setSubject("Mathematic");
        event1.setFileId(1L);

        StudentEvent event2 = new StudentEvent();
        event2.setTeacher("Teacher2");
        event2.setDescription("Description for 2 event");
        event2.setEndDate(LocalDateTime.now().plusMonths(1L));
        event2.setStartDate(LocalDateTime.now());
        event2.setId(2L);
        event2.setSubject("Geometric");
        event2.setFileId(2L);

        StudentEvent event3 = new StudentEvent();
        event3.setTeacher("Teacher3");
        event3.setDescription("Description for 3 event");
        event3.setEndDate(LocalDateTime.now().plusMonths(1L));
        event3.setStartDate(LocalDateTime.now());
        event3.setId(3L);
        event3.setSubject("Analitic");
        event3.setFileId(3L);

        events.add(event1);
        events.add(event2);
        events.add(event3);
    }

    @Override
    public List<StudentEvent> getEvents() {
        return events;
        /*String email = SecurityUtils.getEmail();
        HttpEntity<StudentEvent> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
        String url = serverUrl + "/users" + URL_DELIMITER + email + "/events";
        ResponseEntity<List<StudentEvent>> restAuthResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {});
        return restAuthResponse.getBody();*/

    }

    @Override
    public void uploadHomework(MultipartFile file, String eventId) {
        /*String email = SecurityUtils.getEmail();
        HttpEntity<StudentEvent> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
        String url = serverUrl + "/users" + URL_DELIMITER + email + "/events" + URL_DELIMITER + eventId + "/assignments";
        ResponseEntity<String> restAuthResponse = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);*/
        Assignment assignment = new Assignment();
        assignment.setCommentary("Commentary assignment for eventId" + eventId);
        assignment.setId(4L);
        assignment.setGrade(5);
        events.get((int) Long.parseLong(eventId)).getAssignments().add(assignment);
    }
}

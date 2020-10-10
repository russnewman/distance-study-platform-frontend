package com.netcracker.edu.distancestudyweb.service.impl;



import com.netcracker.edu.distancestudyweb.dto.AssignmentDto;
import com.netcracker.edu.distancestudyweb.dto.EventDto;
import com.netcracker.edu.distancestudyweb.dto.StudentDto;
import com.netcracker.edu.distancestudyweb.dto.wrappers.AssignmentDtoList;
import com.netcracker.edu.distancestudyweb.exception.InternalServiceException;
import com.netcracker.edu.distancestudyweb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    private @Value("${rest.url}") String serverUrl;
    private RestTemplate restTemplate;
    private HttpEntityProvider entityProvider;
    private final StudentService studentService;
    private final EventService eventService;

    @Autowired
    public AssignmentServiceImpl(StudentService studentService, RestTemplate restTemplate, HttpEntityProvider entityProvider, EventService eventService) {
        this.restTemplate = restTemplate;
        this.entityProvider = entityProvider;
        this.studentService = studentService;
        this.eventService = eventService;
    }


    @Override
    public List<AssignmentDto> getAllStudentAssignments(Long studentId) {
        String URL = serverUrl + "/studentAssignments";
        return getStudentAssignmentRestTemplate(
                URL, studentId
        );
    }

    @Override
    public List<AssignmentDto> getAllStudentSubjectAssignments(Long studentId, Long subjectId) {
        return null;
    }

    @Override
    public List<AssignmentDto> getAssessedStudentAssignments(Long studentId) {
        String URL = serverUrl + "/studentAssessedAssignments";
        return getStudentAssignmentRestTemplate(
                URL, studentId
        );
    }

    @Override
    public List<AssignmentDto> getUnassessedStudentAssignments(Long studentId) {
        String URL = serverUrl + "/studentUnassessedAssignments";
        return getStudentAssignmentRestTemplate(
                URL, studentId
        );
    }

    @Override
    public List<AssignmentDto> getActiveStudentAssignments(Long studentId) {
        String URL = serverUrl + "/studentActiveAssignments";
        return getStudentAssignmentRestTemplate(
                URL, studentId
        );
    }

    @Override
    public List<AssignmentDto> getEventStudentAssignments(Long studentId, Long eventId) {
        String URL = serverUrl + "/studentEventAssignments";
        return getStudentAssignmentRestTemplate(
                URL, studentId, eventId
        );
    }

    @Override
    public List<AssignmentDto> getEventStudentAssessedAssignments(Long studentId, Long eventId) {
        String URL = serverUrl + "/studentEventAssessedAssignments";
        return getStudentAssignmentRestTemplate(
                URL, studentId, eventId
        );
    }

    @Override
    public List<AssignmentDto> getEventStudentUnassessedAssignments(Long studentId, Long eventId) {
        String URL = serverUrl + "/studentEventUnassessedAssignments";
        return getStudentAssignmentRestTemplate(
                URL, studentId, eventId
        );
    }

    @Override
    public void save(AssignmentDto assignment) {
//        String URL = baseUri + "addAssignment";
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        //headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//
//        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
//        form.add("file", assignment.getDbFile().getFile());
//        form.add("eventId", assignment.getEvent().getId());
//        form.add("commentary", assignment.getCommentary());
//
//        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(form, headers);
//        restTemplate.postForEntity(URL, request, AssignmentDto.class);
    }


    @Override
    public void update(AssignmentDto assignment) {

        try{
            HttpEntity<?> httpEntity = entityProvider.getDefaultWithTokenFromContext(assignment, null);
            Map<String, Object> parameters = new HashMap<>();
            String url = ServiceUtils.injectParamsInUrl(serverUrl + "/updateAssignment", parameters);
            restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        }
        catch (UnsupportedEncodingException e) {
            throw new InternalServiceException(e);
        }
    }


    private List<AssignmentDto> getStudentAssignmentRestTemplate(String URL, Long studentId){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("studentId", studentId);
        ResponseEntity<List<AssignmentDto>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AssignmentDto>>() {}
        );
        return response.getBody();
    }


    private List<AssignmentDto> getStudentAssignmentRestTemplate(String URL, Long studentId, Long eventId){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("studentId", studentId)
                .queryParam("eventId", eventId);
        ResponseEntity<List<AssignmentDto>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AssignmentDto>>() {}
        );
        return response.getBody();
    }


    private AssignmentDto saveEmptyAssignment(Long eventId, Long studentId){

        try{
            HttpEntity<?> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("eventId", eventId);
            parameters.put("studentId", studentId);
            String url = ServiceUtils.injectParamsInUrl(serverUrl + "/saveEmptyAssignment", parameters);
            ResponseEntity<AssignmentDto> restAuthResponse = restTemplate.exchange(url, HttpMethod.POST, httpEntity, AssignmentDto.class);
            return restAuthResponse.getBody();
        }
        catch (UnsupportedEncodingException e) {
            throw new InternalServiceException(e);
        }
    }



    @Override
    public List<List<AssignmentDto>> getAssignmentsByEvent(Long eventId, Long groupId) {

        List<AssignmentDto> assignments;
        try{
            HttpEntity<?> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("eventId", eventId);
            String url = ServiceUtils.injectParamsInUrl(serverUrl + "/getAssignmentsByEvent", parameters);
            ResponseEntity<AssignmentDtoList> restAuthResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, AssignmentDtoList.class);
            assignments = restAuthResponse.getBody().getAssignments();
        }
        catch (UnsupportedEncodingException e) {
            throw new InternalServiceException(e);
        }


        List<StudentDto> Students = studentService.getStudentsByGroup(groupId);

        List<AssignmentDto> assessedAssignments = new ArrayList<>();
        List<AssignmentDto> unassessedAssignments = new ArrayList<>();
        List<List<AssignmentDto>> res = new ArrayList<>(2);

        EventDto currentEvent = eventService.getEventById(eventId);
        LocalDateTime deadlineTime = currentEvent.getEndDate();
        LocalDateTime currentTime = LocalDateTime.now();


        outer:
        for(StudentDto student: Students){
            for(AssignmentDto assignment: assignments) {
                if (assignment.getStudent().getId().equals(student.getId())) {
                    if (assignment.getGrade() == null) {
                        unassessedAssignments.add(assignment);
                    } else {
                        assessedAssignments.add(assignment);
                    }
                    continue outer;
                }
            }
            if (currentTime.isBefore(deadlineTime)){
                AssignmentDto emptyAssignment = new AssignmentDto();
                emptyAssignment.setStudent(student);
                unassessedAssignments.add(emptyAssignment);
            }
            else{
                AssignmentDto savingEmptyAssignment = saveEmptyAssignment(eventId, student.getId());
                assessedAssignments.add(savingEmptyAssignment);
            }
        }

        studentsNameSorting(assessedAssignments);
        studentsNameSorting(unassessedAssignments);

        res.add(assessedAssignments);
        res.add(unassessedAssignments);
        return res;
    }


    private void studentsNameSorting(List<AssignmentDto> assignments) {
        assignments.sort(new Comparator<AssignmentDto>() {
            @Override
            public int compare(AssignmentDto o1, AssignmentDto o2) {
                int res = o1.getStudent().getName().compareTo(o2.getStudent().getName());
                if (res == 0){
                    res = o1.getStudent().getSurname().compareTo(o2.getStudent().getSurname());
                }
                return res;
            }
        });
    }
}


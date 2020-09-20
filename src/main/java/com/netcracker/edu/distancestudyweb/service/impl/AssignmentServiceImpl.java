package com.netcracker.edu.distancestudyweb.service.impl;



import com.netcracker.edu.distancestudyweb.dto.AssignmentDto;
import com.netcracker.edu.distancestudyweb.dto.StudentDto;
import com.netcracker.edu.distancestudyweb.dto.wrappers.AssignmentDtoList;
import com.netcracker.edu.distancestudyweb.service.AssignmentService;
import com.netcracker.edu.distancestudyweb.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    final static private String baseUri = "http://localhost:8080/";

    private final StudentService studentService;

    @Autowired
    public AssignmentServiceImpl(StudentService studentService) {
        this.studentService = studentService;
    }


    @Override
    public List<AssignmentDto> getAllStudentAssignments(Long studentId) {
        String URL = baseUri + "studentAssignments";
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
        String URL = baseUri + "studentAssessedAssignments";
        return getStudentAssignmentRestTemplate(
                URL, studentId
        );
    }

    @Override
    public List<AssignmentDto> getUnassessedStudentAssignments(Long studentId) {
        String URL = baseUri + "studentUnassessedAssignments";
        return getStudentAssignmentRestTemplate(
                URL, studentId
        );
    }

    @Override
    public List<AssignmentDto> getActiveStudentAssignments(Long studentId) {
        String URL = baseUri + "studentActiveAssignments";
        return getStudentAssignmentRestTemplate(
                URL, studentId
        );
    }

    @Override
    public List<AssignmentDto> getEventStudentAssignments(Long studentId, Long eventId) {
        String URL = baseUri + "studentEventAssignments";
        return getStudentAssignmentRestTemplate(
                URL, studentId, eventId
        );
    }

    @Override
    public List<AssignmentDto> getEventStudentAssessedAssignments(Long studentId, Long eventId) {
        String URL = baseUri + "studentEventAssessedAssignments";
        return getStudentAssignmentRestTemplate(
                URL, studentId, eventId
        );
    }

    @Override
    public List<AssignmentDto> getEventStudentUnassessedAssignments(Long studentId, Long eventId) {
        String URL = baseUri + "studentEventUnassessedAssignments";
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
        String URL = baseUri + "updateAssignment";
        RestTemplate restTemplate = new RestTemplate();


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AssignmentDto> request = new HttpEntity<>(assignment, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(builder.toUriString(),request, String.class);

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
        String URL = baseUri + "/saveEmptyAssignment";

        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("eventId", eventId)
                .queryParam("studentId", studentId);


        ResponseEntity<AssignmentDto> response
                = restTemplate.getForEntity(builder.toUriString(), AssignmentDto.class);

        return response.getBody();
    }



    @Override
    public List<List<AssignmentDto>> getAssignmentsByEvent(Long eventId, Long groupId) {
        String URL = baseUri + "getAssignmentsByEvent";
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("eventId", eventId);

        ResponseEntity<AssignmentDtoList> response = restTemplate.getForEntity(builder.toUriString(), AssignmentDtoList.class);

        List<AssignmentDto> assignments = response.getBody().getAssignments();
        List<StudentDto> Students = studentService.getStudentsByGroup(groupId);

        List<AssignmentDto> assessedAssignments = new ArrayList<>();
        List<AssignmentDto> unassessedAssignments = new ArrayList<>();
        List<List<AssignmentDto>> res = new ArrayList<>(2);


        outer:
        for(StudentDto student: Students){
            for(AssignmentDto assignment: assignments){
                if (assignment.getStudent().getId().equals(student.getId())){
                    if (assignment.getGrade() == null) {
                        unassessedAssignments.add(assignment);
                    } else {
                        assessedAssignments.add(assignment);
                    }
                    continue outer;
                }
            }
            AssignmentDto savingAssignment = saveEmptyAssignment(eventId, student.getId());
            unassessedAssignments.add(savingAssignment);
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


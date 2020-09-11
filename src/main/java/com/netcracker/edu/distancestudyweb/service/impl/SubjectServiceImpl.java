package com.netcracker.edu.distancestudyweb.service.impl;


import com.netcracker.edu.distancestudyweb.dto.SubjectDto;
import com.netcracker.edu.distancestudyweb.dto.SubjectDtoList;
import com.netcracker.edu.distancestudyweb.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.List;
import java.util.Objects;

@Service
public class SubjectServiceImpl implements SubjectService {
    final static private String restUrl = "http://localhost:8080/";

    @Override
    public SubjectDtoList getAllSubjects(){
        String URL = restUrl + "allSubjects";
        return getSubjectRestTemplate(URL);
    }

    public SubjectDtoList getSubjectRestTemplate(String URL){

        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL);
        ResponseEntity<SubjectDtoList> response
                = restTemplate.getForEntity(builder.toUriString(), SubjectDtoList.class);
        return new SubjectDtoList(Objects.requireNonNull(response.getBody()).getSubjects());
    }




    @Override
    public List<SubjectDto> getSubjectsByTeacherId(Long teacherId) {

        RestTemplate restTemplate = new RestTemplate();
        String URL = restUrl + "subjectsByTeacher";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("teacherId", teacherId);
        ResponseEntity<SubjectDtoList> response
                = restTemplate.getForEntity(builder.toUriString(), SubjectDtoList.class);
        return response.getBody().getSubjects();
    }
}

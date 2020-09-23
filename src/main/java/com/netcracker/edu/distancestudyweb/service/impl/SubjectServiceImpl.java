package com.netcracker.edu.distancestudyweb.service.impl;


import com.netcracker.edu.distancestudyweb.domain.Subject;
import com.netcracker.edu.distancestudyweb.dto.SubjectDto;
import com.netcracker.edu.distancestudyweb.dto.wrappers.SubjectDtoList;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import com.netcracker.edu.distancestudyweb.service.SubjectService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.List;
import java.util.Objects;

@Service
public class SubjectServiceImpl implements SubjectService {
    final static private String restUrl = "http://localhost:8080/";
    final private HttpEntityProvider entityProvider;

    public  SubjectServiceImpl(HttpEntityProvider entityProvider){
        this.entityProvider = entityProvider;
    }
    @Override
    public List<Subject> getAll() {
        String URL = restUrl + "allSubjects";
        return getPureSubjectRestTemplate(URL);
    }

    @Override
    public SubjectDtoList getAllSubjects(){
        String URL = restUrl + "allSubjects";
        return getSubjectRestTemplate(URL);
    }

    public SubjectDtoList getSubjectRestTemplate(String URL){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL);
        HttpEntity<Subject> entity = entityProvider.getWithTokenFromContext(null, null, null);
        ResponseEntity<SubjectDtoList> response
                = restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        entity,
                        SubjectDtoList.class);
        return new SubjectDtoList(Objects.requireNonNull(response.getBody()).getSubjects());
    }

    private List<Subject> getPureSubjectRestTemplate(String URL){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL);
        HttpEntity<Subject> entity = entityProvider.getWithTokenFromContext(null, null, null);
        ResponseEntity<List<Subject>> response
                = restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<>() {}
                        );
        return response.getBody();
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

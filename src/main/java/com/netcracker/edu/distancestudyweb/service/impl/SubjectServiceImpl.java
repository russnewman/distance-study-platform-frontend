package com.netcracker.edu.distancestudyweb.service.impl;


import com.netcracker.edu.distancestudyweb.dto.SubjectDto;
import com.netcracker.edu.distancestudyweb.dto.SubjectDtoList;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import com.netcracker.edu.distancestudyweb.service.SubjectService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.netcracker.edu.distancestudyweb.domain.Subject;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import com.netcracker.edu.distancestudyweb.service.SubjectService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Objects;

@Service
public class SubjectServiceImpl implements SubjectService {
    final static private String restUrl = "http://localhost:8080/";
    private @Value("${rest.url}") String serverUrl;
    private RestTemplate restTemplate;
    private HttpEntityProvider entityProvider;

    public SubjectServiceImpl(RestTemplate restTemplate, HttpEntityProvider entityProvider) {
        this.restTemplate = restTemplate;
        this.entityProvider = entityProvider;
    }
    @Override
    public SubjectDtoList getAllSubjects(){
        String URL = restUrl + "allSubjects";
        return getSubjectRestTemplate(URL);
    }

    @Override
    public List<Subject> getAll() {
        String url = serverUrl + "/subjects";
        HttpEntity<Subject> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
        ResponseEntity<List<Subject>> restAuthResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {});
        return restAuthResponse.getBody();
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

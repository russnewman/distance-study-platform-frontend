package com.netcracker.edu.distancestudyweb.service.impl;

import com.netcracker.edu.distancestudyweb.domain.StudentEvent;
import com.netcracker.edu.distancestudyweb.domain.Subject;
import com.netcracker.edu.distancestudyweb.dto.SubjectDto;
import com.netcracker.edu.distancestudyweb.dto.homework.EventFormRequest;
import com.netcracker.edu.distancestudyweb.dto.wrappers.SubjectDtoList;
import com.netcracker.edu.distancestudyweb.exception.InternalServiceException;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import com.netcracker.edu.distancestudyweb.service.ServiceUtils;
import com.netcracker.edu.distancestudyweb.service.SubjectService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class SubjectServiceImpl implements SubjectService {

    private @Value("${rest.url}") String serverUrl;
    private RestTemplate restTemplate;
    private HttpEntityProvider entityProvider;

    public SubjectServiceImpl(RestTemplate restTemplate, HttpEntityProvider entityProvider) {
        this.restTemplate = restTemplate;
        this.entityProvider = entityProvider;
    }



    @Override
    public SubjectDtoList getAllSubjects(){
        String URL = serverUrl + "allSubjects";
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

        try{
            HttpEntity<?> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("teacherId", teacherId);
            String url = ServiceUtils.injectParamsInUrl(serverUrl + "/subjectsByTeacher", parameters);
            ResponseEntity<SubjectDtoList> restAuthResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, SubjectDtoList.class);

            return restAuthResponse.getBody().getSubjects();
        }
        catch (UnsupportedEncodingException e) {
            throw new InternalServiceException(e);
        }
    }


    @Override
    public List<Subject> getAll() {
        String url = serverUrl + "/subjects";
        HttpEntity<Subject> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
        ResponseEntity<List<Subject>> restAuthResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {});
        return restAuthResponse.getBody();
    }
}

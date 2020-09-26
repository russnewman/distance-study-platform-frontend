package com.netcracker.edu.distancestudyweb.service.impl;

import com.netcracker.edu.distancestudyweb.domain.Subject;
import com.netcracker.edu.distancestudyweb.dto.SubjectDto;
import com.netcracker.edu.distancestudyweb.exception.InternalServiceException;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import com.netcracker.edu.distancestudyweb.service.ServiceUtils;
import com.netcracker.edu.distancestudyweb.service.SubjectService;
import com.netcracker.edu.distancestudyweb.utils.RestRequestConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubjectServiceImpl implements SubjectService {

    final private HttpEntityProvider entityProvider;
    private @Value("${rest.url}") String serverUrl;
    final private RestTemplate restTemplate;

    public  SubjectServiceImpl(HttpEntityProvider entityProvider, RestTemplate restTemplate){
        this.entityProvider = entityProvider;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<SubjectDto> getAllSubjects(){
        String url = serverUrl + "/allSubjects";
        return getSubjectRestTemplate(url);
    }

    private List<SubjectDto> getSubjectRestTemplate(String url){
        RestRequestConstructor<List<SubjectDto>> ctor = new RestRequestConstructor<>(entityProvider);
        return ctor.getRestTemplate(url, null);
    }

    private List<Subject> getPureSubjectRestTemplate(String url){
        RestRequestConstructor<List<Subject>> ctor = new RestRequestConstructor<>(entityProvider);
        return ctor.getRestTemplate(url, null);
    }



    @Override
    public List<SubjectDto> getSubjectsByTeacherId(Long teacherId) {

        try{
            HttpEntity<?> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("teacherId", teacherId);
            String url = ServiceUtils.injectParamsInUrl(serverUrl + "/subjectsByTeacher", parameters);
            ResponseEntity<List<SubjectDto>> restAuthResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<SubjectDto>>() {});

            return restAuthResponse.getBody();
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

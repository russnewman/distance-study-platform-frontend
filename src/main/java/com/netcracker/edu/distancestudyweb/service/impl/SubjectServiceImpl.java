package com.netcracker.edu.distancestudyweb.service.impl;

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
    public List<Subject> getAll() {
        String url = serverUrl + "/subjects";
        HttpEntity<Subject> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
        ResponseEntity<List<Subject>> restAuthResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {});
        return restAuthResponse.getBody();
    }
}

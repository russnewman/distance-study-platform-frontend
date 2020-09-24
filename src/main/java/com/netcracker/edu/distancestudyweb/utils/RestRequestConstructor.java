package com.netcracker.edu.distancestudyweb.utils;

import com.netcracker.edu.distancestudyweb.domain.Subject;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class RestRequestConstructor<T> {
    final private HttpEntityProvider entityProvider;

    public RestRequestConstructor(HttpEntityProvider entityProvider) {
        this.entityProvider = entityProvider;
    }

    public T getRestTemplate(String url, UriComponentsBuilder builder){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<T> entity = entityProvider.getWithTokenFromContext(null, null, null);
        ResponseEntity<T> response
                = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }
}

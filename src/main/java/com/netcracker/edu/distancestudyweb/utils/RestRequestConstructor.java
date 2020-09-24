package com.netcracker.edu.distancestudyweb.utils;

import com.netcracker.edu.distancestudyweb.domain.Subject;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

public class RestRequestConstructor<T> {
    final private HttpEntityProvider entityProvider;

    public RestRequestConstructor(HttpEntityProvider entityProvider) {
        this.entityProvider = entityProvider;
    }

    public T getRestTemplate(String url, @Nullable MultiValueMap<String, String> params){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        if(params != null) builder.queryParams(params);
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

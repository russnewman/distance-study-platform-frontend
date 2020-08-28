package com.netcracker.edu.distancestudyweb.service.impl;

import com.netcracker.edu.distancestudyweb.domain.ClientPrincipal;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


import java.util.Arrays;

@Component
public class HttpEntityProviderImpl implements HttpEntityProvider {
    private HttpHeaders getHeaders(MediaType content, MediaType... mediaTypes) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(mediaTypes));
        headers.setContentType(content);
        return headers;
    }

    @Override
    public <T> HttpEntity<T> get(T body, MediaType content, MediaType... mediaTypes) {
        HttpHeaders headers = getHeaders(content, mediaTypes);
        if (body != null) {
            return new HttpEntity<>(body, headers);
        } else {
            return new HttpEntity<>(headers);
        }
    }

    @Override
    public <T> HttpEntity<T> getDefault(T body) {
        return get(body, MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON);
    }

    @Override
    public <T> HttpEntity<T> getWithTokenFromContext(T body, MediaType content, MediaType... mediaTypes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new IllegalStateException("There is no authenticated client");
        }
        HttpHeaders headers = getHeaders(content,mediaTypes);
        ClientPrincipal principal = (ClientPrincipal) auth.getPrincipal();
        String token = principal.getToken();
        headers.add("Authorization", "Bearer " + token);
        if (body != null) {
            return new HttpEntity<T>(body, headers);
        } else {
            return new HttpEntity<T>(headers);
        }

    }

    @Override
    public <T> HttpEntity<T> getDefaultWithTokenFromContext(T body) {
        return getWithTokenFromContext(body, MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON);
    }

    @Override
    public <T> HttpEntity<T> getDefaultWithToken(String token, T body) {
        return getWithToken(token, body, MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON);
    }

    @Override
    public <T> HttpEntity<T> getWithToken(String token, T body, MediaType content, MediaType... mediaTypes) {
        HttpHeaders headers = getHeaders(content, mediaTypes);
        headers.add("Authorization", "Bearer " + token);
        if (body != null) {
            return new HttpEntity<>(body,headers);
        } else {
            return new HttpEntity<>(headers);
        }

    }
}

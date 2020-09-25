package com.netcracker.edu.distancestudyweb.service.impl;

import com.netcracker.edu.distancestudyweb.domain.ClientPrincipal;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


import java.util.Arrays;
import java.util.Map;

@Component
public class HttpEntityProviderImpl implements HttpEntityProvider {
    private HttpHeaders getHeaders(@Nullable Map<String, String> parameters, MediaType content, MediaType... mediaTypes) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(mediaTypes));
        headers.setContentType(content);
        if (parameters != null) {
            parameters.forEach(headers::add);
        }
        return headers;
    }

    @Override
    public <T> HttpEntity<T> get(@Nullable T body, @Nullable Map<String, String> parameters, MediaType content, MediaType... mediaTypes) {
        HttpHeaders headers = getHeaders(parameters, content, mediaTypes);
        if (body != null) {
            return new HttpEntity<>(body, headers);
        } else {
            return new HttpEntity<>(headers);
        }
    }

    @Override
    public <T> HttpEntity<T> getDefault(@Nullable T body, @Nullable Map<String, String> parameters) {
        return get(body, parameters, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);
    }

    @Override
    public <T> HttpEntity<T> getWithTokenFromContext(@Nullable T body, @Nullable Map<String, String> parameters, MediaType content, MediaType... mediaTypes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new IllegalStateException("There is no authenticated client");
        }
        ClientPrincipal principal = (ClientPrincipal) auth.getPrincipal();
        String token = principal.getToken();
        return getEntityWithTokenBodyAndParameters(body, parameters, content, token, mediaTypes);
    }


    private <T> HttpEntity<T> getEntityWithTokenBodyAndParameters(@Nullable T body, @Nullable Map<String, String> parameters, MediaType content, String token, MediaType[] mediaTypes) {
        HttpHeaders headers = getHeaders(parameters, content, mediaTypes);
        headers.add("Authorization", "Bearer " + token);
        if (body != null) {
            return new HttpEntity<T>(body, headers);
        } else {
            return new HttpEntity<T>(headers);
        }
    }

    @Override
    public <T> HttpEntity<T> getDefaultWithTokenFromContext(@Nullable T body, @Nullable Map<String, String> parameters) {
        return getWithTokenFromContext(body, parameters, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);
    }

    @Override
    public <T> HttpEntity<T> getDefaultWithSpecifiedToken(String token, T body, Map<String, String> parameters) {
        return getWithSpecifiedToken(token, body, parameters,  MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);
    }

    @Override
    public <T> HttpEntity<T> getWithSpecifiedToken(String token, T body, Map<String, String> parameters, MediaType content, MediaType... mediaTypes) {
        return getEntityWithTokenBodyAndParameters(body, parameters, content, token, mediaTypes);
    }



    @Override
    public <T> HttpEntity<T> getDefaultWithTokenFromContextMULTIPART_FORM_DATA(@Nullable T body, @Nullable Map<String, String> parameters) {
        return getWithTokenFromContext(body, parameters, MediaType.MULTIPART_FORM_DATA, MediaType.MULTIPART_FORM_DATA);
    }
}

package com.netcracker.edu.distancestudyweb.service;

import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class ServiceUtils {
    public static String injectParamsInUrl(String baseUrl, Map<String,Object> params) throws UnsupportedEncodingException {
        UriComponentsBuilder componentsBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        for (Map.Entry<String,Object> entry : params.entrySet()) {
            componentsBuilder.queryParam(entry.getKey(), entry.getValue());
        }
        return componentsBuilder.toUriString();
    }
}

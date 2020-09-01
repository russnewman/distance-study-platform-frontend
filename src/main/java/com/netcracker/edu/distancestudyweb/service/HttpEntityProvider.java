package com.netcracker.edu.distancestudyweb.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;

import java.util.Map;

public interface HttpEntityProvider {
    <T> HttpEntity<T> get(@Nullable T body, @Nullable Map<String, String> parameters, MediaType content, MediaType... mediaTypes);
    <T> HttpEntity<T> getDefault(@Nullable T body, @Nullable Map<String, String> parameters);
    <T> HttpEntity<T> getWithTokenFromContext(@Nullable T body, @Nullable Map<String, String> parameters, MediaType content, MediaType... mediaTypes);
    <T> HttpEntity<T> getDefaultWithTokenFromContext(@Nullable T body, @Nullable Map<String, String> parameters);
}

package com.netcracker.edu.distancestudyweb.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;

public interface HttpEntityProvider {
    <T> HttpEntity<T> get(@Nullable T body, MediaType content, MediaType... mediaTypes);
    <T> HttpEntity<T> getDefault(@Nullable T body);
    <T> HttpEntity<T> getWithTokenFromContext(@Nullable T body, MediaType content, MediaType... mediaTypes);
    <T> HttpEntity<T> getDefaultWithTokenFromContext(@Nullable T body);
    <T> HttpEntity<T> getDefaultWithToken(String token, @Nullable T body);
    <T> HttpEntity<T> getWithToken(String token, @Nullable T body, MediaType content, MediaType... mediaTypes);
}

package com.netcracker.edu.distancestudyweb.service.impl.user;

import com.netcracker.edu.distancestudyweb.dto.user.GetUserInfoRequest;
import com.netcracker.edu.distancestudyweb.dto.user.GetUserInfoResponse;
import com.netcracker.edu.distancestudyweb.exception.UserNotFoundException;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import com.netcracker.edu.distancestudyweb.service.UserService;
import com.netcracker.edu.distancestudyweb.service.impl.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.netcracker.edu.distancestudyweb.controller.ControllerUtils.URL_DELIMITER;

@Service
public class UserServiceImpl implements UserService {
    private @Value("${rest.url}") String serverUrl;
    private String usersEndpoint = "/users";
    private RestTemplate restTemplate;
    private HttpEntityProvider entityProvider;

    public UserServiceImpl(RestTemplate restTemplate, HttpEntityProvider entityProvider) {
        this.restTemplate = restTemplate;
        this.entityProvider = entityProvider;
    }

    @Override
    @Cacheable(value = "users", key = "#request.getEmail()")
    public GetUserInfoResponse getUserInfo(GetUserInfoRequest request) throws UserNotFoundException{
        HttpEntity<String> httpEntity = entityProvider.getDefault(null);
        String url = serverUrl + usersEndpoint + URL_DELIMITER + request.getEmail();
        ResponseEntity<GetUserInfoResponse> restAuthResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, GetUserInfoResponse.class);
        if (restAuthResponse.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
            throw new UserNotFoundException("User with email: " + SecurityUtils.getEmail() + " not found");
        }
        return restAuthResponse.getBody();
    }
}

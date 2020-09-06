package com.netcracker.edu.distancestudyweb.service.impl.user;

import com.netcracker.edu.distancestudyweb.domain.User;
import com.netcracker.edu.distancestudyweb.dto.user.ChangePasswordRequest;
import com.netcracker.edu.distancestudyweb.exception.DifferentPasswordsException;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import com.netcracker.edu.distancestudyweb.service.UserService;
import com.netcracker.edu.distancestudyweb.service.impl.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
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
    public User getUserInfo() {
        String email = SecurityUtils.getEmail();
        HttpEntity<User> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
        String url = serverUrl + usersEndpoint + URL_DELIMITER + email;
        ResponseEntity<User> restAuthResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, User.class);
        return restAuthResponse.getBody();
    }

    @Override
    public void changePassword(ChangePasswordRequest request) throws DifferentPasswordsException {
        String email = SecurityUtils.getEmail();
        HttpEntity<ChangePasswordRequest> httpEntity = entityProvider.getDefaultWithTokenFromContext(request, null);
        String url = serverUrl + usersEndpoint + URL_DELIMITER + email + "/password";
        ResponseEntity<String> restAuthResponse = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);
        if (restAuthResponse.getStatusCode().equals(HttpStatus.CONFLICT)) {
            throw new DifferentPasswordsException("Different passwords");
        }
    }
}

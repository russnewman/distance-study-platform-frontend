package com.netcracker.edu.distancestudyweb.service.impl;


import com.netcracker.edu.distancestudyweb.domain.ClientPrincipal;
import com.netcracker.edu.distancestudyweb.dto.wrappers.GroupDtoList;
import com.netcracker.edu.distancestudyweb.exception.InternalServiceException;
import com.netcracker.edu.distancestudyweb.payload.Response;
import com.netcracker.edu.distancestudyweb.service.DatabaseFileService;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import com.netcracker.edu.distancestudyweb.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


@Service
public class DatabaseFileUiServiceImpl implements DatabaseFileService {
    private @Value("${rest.url}") String serverUrl;
    private RestTemplate restTemplate;
    private HttpEntityProvider entityProvider;

    @Autowired
    public DatabaseFileUiServiceImpl(RestTemplate restTemplate, HttpEntityProvider entityProvider) {
        this.restTemplate = restTemplate;
        this.entityProvider = entityProvider;
    }


    @Override
    public Response saveDatabaseFile(MultipartFile multipartFile) {



            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null) {
                throw new IllegalStateException("There is no authenticated client");
            }
            ClientPrincipal principal = (ClientPrincipal) auth.getPrincipal();
            String token = principal.getToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.add("Authorization", "Bearer " + token);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", multipartFile.getResource());

            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<Response> response
                = restTemplate.postForEntity(serverUrl + "/upload", request, Response.class);

        return response.getBody();










//        try{
//            MultiValueMap<String, Object> body
//                    = new LinkedMultiValueMap<>();
//            body.add("file", multipartFile.getResource());
//
//            HttpEntity<MultiValueMap<String, Object>> httpEntity = entityProvider.getDefaultWithTokenFromContextMULTIPART_FORM_DATA(body,null);
//
//            Map<String, Object> parameters = new HashMap<>();
//            String url = ServiceUtils.injectParamsInUrl(serverUrl + "/upload", parameters);
//            ResponseEntity<Response> restAuthResponse = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Response.class);
//            return restAuthResponse.getBody();
//        }
//        catch (UnsupportedEncodingException e) {
//            throw new InternalServiceException(e);
//        }

    }

    @Override
    public ResponseEntity<Resource> downloadFile(String fileId) {
        try{
            HttpEntity<?> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
            Map<String, Object> parameters = new HashMap<>();
            String url = ServiceUtils.injectParamsInUrl(serverUrl + "/downloadFile/" + fileId, parameters);
            ResponseEntity<ResponseEntity<Resource>> restAuthResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {});
            return restAuthResponse.getBody();
        }
        catch (UnsupportedEncodingException e) {
            throw new InternalServiceException(e);
        }
    }
}

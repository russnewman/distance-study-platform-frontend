package com.netcracker.edu.distancestudyweb.service.impl;


import com.netcracker.edu.distancestudyweb.payload.Response;
import com.netcracker.edu.distancestudyweb.service.DatabaseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


@Service
public class DatabaseFileUiServiceImpl implements DatabaseFileService {
    String restURL = "http://localhost:8080";

    private final RestTemplate restTemplate;

    @Autowired
    public DatabaseFileUiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public Response saveDatabaseFile(MultipartFile multipartFile) {
        String URL = restURL + "/upload";


        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("file", multipartFile.getResource());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);


        ResponseEntity<Response> response
                = restTemplate.postForEntity(URL, request, Response.class);

        return response.getBody();
    }
}

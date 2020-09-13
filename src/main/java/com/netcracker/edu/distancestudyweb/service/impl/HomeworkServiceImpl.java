package com.netcracker.edu.distancestudyweb.service.impl;

import com.netcracker.edu.distancestudyweb.domain.FileInfo;
import com.netcracker.edu.distancestudyweb.domain.StudentEvent;
import com.netcracker.edu.distancestudyweb.dto.homework.AssignmentFormRequest;
import com.netcracker.edu.distancestudyweb.dto.homework.AssignmentRequestDto;
import com.netcracker.edu.distancestudyweb.dto.homework.EventFormRequest;
import com.netcracker.edu.distancestudyweb.exception.InternalServiceException;
import com.netcracker.edu.distancestudyweb.service.HomeworkService;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import com.netcracker.edu.distancestudyweb.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.netcracker.edu.distancestudyweb.controller.ControllerUtils.URL_DELIMITER;

@Service
public class HomeworkServiceImpl implements HomeworkService {
    private @Value("${rest.url}") String serverUrl;
    private RestTemplate restTemplate;
    private HttpEntityProvider entityProvider;

    public HomeworkServiceImpl(RestTemplate restTemplate, HttpEntityProvider entityProvider) {
        this.restTemplate = restTemplate;
        this.entityProvider = entityProvider;
    }

    @Override
    public List<StudentEvent> getEvents(EventFormRequest formRequest) {
        try {
            Long studentId = SecurityUtils.getId();
            HttpEntity<StudentEvent> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("subjectId", formRequest.getSubjectId());
            parameters.put("studentId", studentId);
            String url = ServiceUtils.injectParamsInUrl(serverUrl + "/events", parameters);
            ResponseEntity<List<StudentEvent>> restAuthResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {});
            return restAuthResponse.getBody();
        } catch (UnsupportedEncodingException e) {
            throw new InternalServiceException(e);
        }
    }

    @Override
    public void uploadHomework(AssignmentFormRequest formRequest) {
        try {
            AssignmentRequestDto requestDto = prepareRequestForAssignment(formRequest);
            HttpEntity<AssignmentRequestDto> httpEntity = entityProvider.getDefaultWithTokenFromContext(requestDto, null);
            String url = serverUrl + "/events" + URL_DELIMITER + formRequest.getEventId() + "/assignments";
            restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        } catch (Exception e) {
            throw new InternalServiceException(e);
        }
    }

    private AssignmentRequestDto prepareRequestForAssignment(AssignmentFormRequest formRequest) throws IOException {
        AssignmentRequestDto assignment = new AssignmentRequestDto();
        assignment.setDescription(formRequest.getDescription());
        assignment.setStudentId(SecurityUtils.getId());
        FileInfo fileInfo = new FileInfo();
        fileInfo.setData(formRequest.getFile().getBytes());
        fileInfo.setType(formRequest.getFile().getContentType());
        assignment.setFileInfo(fileInfo);
        return assignment;
    }

    @Override
    public ResponseEntity<Resource> downloadFile(Long fileId) {
        HttpEntity<Resource> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
        String url = serverUrl + "/downloadFile" + URL_DELIMITER + fileId;
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, Resource.class);
    }
}

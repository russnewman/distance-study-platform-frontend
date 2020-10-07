package com.netcracker.edu.distancestudyweb.service.impl;

import com.netcracker.edu.distancestudyweb.controller.ControllerUtils;
import com.netcracker.edu.distancestudyweb.domain.Assignment;
import com.netcracker.edu.distancestudyweb.dto.GetStudentEventsResponseDto;
import com.netcracker.edu.distancestudyweb.dto.homework.DatabaseFileDto;
import com.netcracker.edu.distancestudyweb.domain.StudentEvent;
import com.netcracker.edu.distancestudyweb.dto.homework.AssignmentFormRequest;
import com.netcracker.edu.distancestudyweb.dto.homework.AssignmentRequestDto;
import com.netcracker.edu.distancestudyweb.dto.homework.EventFormRequest;
import com.netcracker.edu.distancestudyweb.exception.ExternalServiceException;
import com.netcracker.edu.distancestudyweb.exception.InternalServiceException;
import com.netcracker.edu.distancestudyweb.service.HomeworkService;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import com.netcracker.edu.distancestudyweb.service.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.netcracker.edu.distancestudyweb.controller.ControllerUtils.URL_DELIMITER;

@Service
@Slf4j
public class HomeworkServiceImpl implements HomeworkService {
    private @Value("${rest.url}") String serverUrl;
    private RestTemplate restTemplate;
    private HttpEntityProvider entityProvider;

    public HomeworkServiceImpl(RestTemplate restTemplate, HttpEntityProvider entityProvider) {
        this.restTemplate = restTemplate;
        this.entityProvider = entityProvider;
    }

    @Override
    public GetStudentEventsResponseDto getEvents(EventFormRequest formRequest) {
        try {
            Long studentId = SecurityUtils.getId();
            HttpEntity<StudentEvent> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
            Map<String, Object> parameters = new HashMap<>();
            if (formRequest.getSubjectId() != null) {
                parameters.putIfAbsent("subjectId", formRequest.getSubjectId());
            }
            parameters.put("studentId", studentId);
            parameters.put("sort", "endDate");
            parameters.put("page", Optional.ofNullable(formRequest.getPage()).map(number -> --number).orElse(0));
            parameters.put("size", 2);
            String url = ServiceUtils.injectParamsInUrl(serverUrl + "/events", parameters);
            ResponseEntity<GetStudentEventsResponseDto> restAuthResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, GetStudentEventsResponseDto.class);
            if (!restAuthResponse.getStatusCode().is2xxSuccessful()) {
                throw new ExternalServiceException("Unexpected code: " + restAuthResponse.getStatusCode().value());
            }
            GetStudentEventsResponseDto responseDto = Objects.requireNonNull(restAuthResponse.getBody());
            List<StudentEvent> events =  responseDto.getEvents();
            events.forEach(event -> event.setAssignments(getAssignments(studentId, event.getId())));
            return responseDto;
        } catch (UnsupportedEncodingException e) {
            log.error("Unsupported encoding exception has occurred while injecting parameters in url", e);
            throw new InternalServiceException(e);
        } catch (ExternalServiceException e) {
            log.error("An unexpected external exception has occurred", e);
            throw e;
        } catch (Exception e ) {
            log.error("An unexpected internal exception has occurred", e);
            throw new InternalServiceException(e);
        }
    }

    private List<Assignment> getAssignments(Long studentId, Long eventId) {
        try {
            HttpEntity<Assignment> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("studentId", studentId);
            String url = ServiceUtils.injectParamsInUrl(serverUrl + "/events/" + eventId + URL_DELIMITER + "/assignments", parameters);
            ResponseEntity<List<Assignment>> restAuthResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {});
            if (!restAuthResponse.getStatusCode().is2xxSuccessful()) {
                throw new ExternalServiceException("Unexpected code: " + restAuthResponse.getStatusCode().value());
            }
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
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            HttpStatus status = response.getStatusCode();
            if (!status.is2xxSuccessful()) {
                log.error("Unexpected status code: " + status.value());
                throw new ExternalServiceException("Unexpected status: " + status.value());
            }
        } catch (Exception e) {
            throw new InternalServiceException(e);
        }
    }

    private AssignmentRequestDto prepareRequestForAssignment(AssignmentFormRequest formRequest) throws IOException {
        AssignmentRequestDto assignment = new AssignmentRequestDto();
        assignment.setCommentary(formRequest.getCommentary());
        assignment.setStudentId(SecurityUtils.getId());
        DatabaseFileDto databaseFileDto = new DatabaseFileDto();
        databaseFileDto.setFile(formRequest.getFile().getBytes());
        databaseFileDto.setFileType(formRequest.getFile().getContentType());
        databaseFileDto.setFileName(formRequest.getFile().getOriginalFilename());
        assignment.setDbFileDto(databaseFileDto);
        return assignment;
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String fileId) {
        HttpEntity<Resource> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
        String url = serverUrl + "/downloadFile" + URL_DELIMITER + fileId;
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, Resource.class);
    }
}

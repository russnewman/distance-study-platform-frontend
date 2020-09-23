package com.netcracker.edu.distancestudyweb.service.impl;

import com.netcracker.edu.distancestudyweb.dto.GroupDto;
import com.netcracker.edu.distancestudyweb.dto.ScheduleDto;
import com.netcracker.edu.distancestudyweb.dto.wrappers.GroupDtoList;
import com.netcracker.edu.distancestudyweb.dto.wrappers.ScheduleDtoList;
import com.netcracker.edu.distancestudyweb.exception.InternalServiceException;
import com.netcracker.edu.distancestudyweb.service.GroupService;

import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import com.netcracker.edu.distancestudyweb.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class GroupServiceImpl implements GroupService {

    private @Value("${rest.url}") String serverUrl;
    private RestTemplate restTemplate;
    private HttpEntityProvider entityProvider;

    public GroupServiceImpl(RestTemplate restTemplate, HttpEntityProvider entityProvider) {
        this.restTemplate = restTemplate;
        this.entityProvider = entityProvider;
    }




    @Override
    public List<GroupDto> findGroupsByTeacherAndSubject(Long teacherId, String subjectName) {


        try{
            HttpEntity<?> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("teacherId", teacherId);
            parameters.put("subjectName", subjectName);
            String url = ServiceUtils.injectParamsInUrl(serverUrl + "/findGroupsByTeacherAndSubject", parameters);
            ResponseEntity<GroupDtoList> restAuthResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, GroupDtoList.class);
            return restAuthResponse.getBody().getGroups();
        }
        catch (UnsupportedEncodingException e) {
            throw new InternalServiceException(e);
        }


//        String URL = restUrl + "findGroupsByTeacherAndSubject";
//        RestTemplate restTemplate = new RestTemplate();
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
//                .queryParam("teacherId", teacherId)
//                .queryParam("subjectName", subjectName);
//
//        ResponseEntity<GroupDtoList> response
//                = restTemplate.getForEntity(builder.toUriString(), GroupDtoList.class);
//        return response.getBody().getGroups();
    }


}

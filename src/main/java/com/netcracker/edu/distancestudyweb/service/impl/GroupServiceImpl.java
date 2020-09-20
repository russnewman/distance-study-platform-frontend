package com.netcracker.edu.distancestudyweb.service.impl;

import com.netcracker.edu.distancestudyweb.dto.GroupDto;
import com.netcracker.edu.distancestudyweb.dto.wrappers.GroupDtoList;
import com.netcracker.edu.distancestudyweb.service.GroupService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@Service
public class GroupServiceImpl implements GroupService {

    String restUrl = "http://localhost:8080/";
    @Override
    public List<GroupDto> findGroupsByTeacherAndSubject(Long teacherId, String subjectName) {

        String URL = restUrl + "findGroupsByTeacherAndSubject";
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("teacherId", teacherId)
                .queryParam("subjectName", subjectName);

        ResponseEntity<GroupDtoList> response
                = restTemplate.getForEntity(builder.toUriString(), GroupDtoList.class);
        return response.getBody().getGroups();
    }


}

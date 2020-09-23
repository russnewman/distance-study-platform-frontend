package com.netcracker.edu.distancestudyweb.service.impl;

import com.netcracker.edu.distancestudyweb.dto.GroupDto;
import com.netcracker.edu.distancestudyweb.dto.StudentDto;
import com.netcracker.edu.distancestudyweb.dto.wrappers.GroupDtoList;
import com.netcracker.edu.distancestudyweb.dto.wrappers.StudentDtoList;
import com.netcracker.edu.distancestudyweb.exception.InternalServiceException;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import com.netcracker.edu.distancestudyweb.service.ServiceUtils;
import com.netcracker.edu.distancestudyweb.service.StudentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    private @Value("${rest.url}") String serverUrl;
    private RestTemplate restTemplate;
    private HttpEntityProvider entityProvider;

    public StudentServiceImpl(RestTemplate restTemplate, HttpEntityProvider entityProvider) {
        this.restTemplate = restTemplate;
        this.entityProvider = entityProvider;
    }



    @Override
    public StudentDto getStudentByEmail(String email) {
        return null;
    }

    @Override
    public StudentDto getStudent(Long id) {
        return null;
    }



    @Override
    public List<StudentDto> getStudentsByGroup(Long groupId) {


        try {
            HttpEntity<?> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("groupId", groupId);
            String url = ServiceUtils.injectParamsInUrl(serverUrl + "/students/getStudentsByGroup", parameters);
            ResponseEntity<StudentDtoList> restAuthResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, StudentDtoList.class);

            List<StudentDto> students = restAuthResponse.getBody().getStudents();
            students.sort(new Comparator<StudentDto>() {
                @Override
                public int compare(StudentDto o1, StudentDto o2) {
                    Integer res = o1.getSurname().compareTo(o2.getSurname());
                    if (res == 0) res = o1.getName().compareTo(o2.getName());
                    return res;
                }
            });
            return students;

        } catch (UnsupportedEncodingException e) {
            throw new InternalServiceException(e);
        }


//
//        String URL = restUrl + "students/getStudentsByGroup";
//        RestTemplate restTemplate = new RestTemplate();
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
//                .queryParam("groupId", groupId);
//
//        ResponseEntity<StudentDtoList> response
//                = restTemplate.getForEntity(builder.toUriString(), StudentDtoList.class);
//
//
//        List<StudentDto> students = response.getBody().getStudents();

    }
}

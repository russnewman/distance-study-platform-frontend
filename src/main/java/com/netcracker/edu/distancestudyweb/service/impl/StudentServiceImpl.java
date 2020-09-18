package com.netcracker.edu.distancestudyweb.service.impl;

import com.netcracker.edu.distancestudyweb.dto.GroupDto;
import com.netcracker.edu.distancestudyweb.dto.StudentDto;
import com.netcracker.edu.distancestudyweb.dto.wrappers.GroupDtoList;
import com.netcracker.edu.distancestudyweb.dto.wrappers.StudentDtoList;
import com.netcracker.edu.distancestudyweb.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Comparator;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
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
        String restUrl = "http://localhost:8080/";

        String URL = restUrl + "students/getStudentsByGroup";
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("groupId", groupId);

        ResponseEntity<StudentDtoList> response
                = restTemplate.getForEntity(builder.toUriString(), StudentDtoList.class);


        List<StudentDto> students = response.getBody().getStudents();

        students.sort(new Comparator<StudentDto>() {
            @Override
            public int compare(StudentDto o1, StudentDto o2) {
                Integer res =  o1.getSurname().compareTo(o2.getSurname());
                if (res == 0) res = o1.getName().compareTo(o2.getName());
                return res;
            }
        });
        return students;

    }
}

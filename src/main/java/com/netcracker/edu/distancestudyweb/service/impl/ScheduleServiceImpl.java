package com.netcracker.edu.distancestudyweb.service.impl;


import com.netcracker.edu.distancestudyweb.dto.GroupDto;
import com.netcracker.edu.distancestudyweb.dto.ScheduleDto;
import com.netcracker.edu.distancestudyweb.dto.wrappers.ScheduleDtoList;
import com.netcracker.edu.distancestudyweb.dto.SubjectDto;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import com.netcracker.edu.distancestudyweb.service.ScheduleService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {


    final static private String baseUri = "http://localhost:8080/";
    final private HttpEntityProvider entityProvider;

    public ScheduleServiceImpl(HttpEntityProvider entityProvider) {
        this.entityProvider = entityProvider;
    }

    @Override
    public ScheduleDtoList getStudentFullSchedule(Long studentId){
        String URL = baseUri + "full";
        return getStudentScheduleRestTemplate(URL, studentId);
    }

    @Override
    public ScheduleDtoList getStudentTodaySchedule(Long studentId){
        String URL = baseUri + "todayStudentSchedule";
        return getStudentScheduleRestTemplate(URL, studentId);
    }

    @Override
    public ScheduleDtoList getStudentTomorrowSchedule(Long studentId) {
        String URL = baseUri + "tomorrowStudentSchedule";
        return getStudentScheduleRestTemplate(URL, studentId);
    }

    @Override
    public SubjectDto getStudentCurrentSubject(Long studentId) {
        String URL = baseUri + "currentStudentSubject";
        return getSubjectRestTemplate(URL, studentId);
    }

    @Override
    public SubjectDto getStudentNextSubject(Long studentId) {
        String URL = baseUri + "nextStudentSubject";
        return getSubjectRestTemplate(URL, studentId);
    }

    @Override
    public ScheduleDtoList getStudentSubjectSchedule(Long studentId, Long subjectId){
        String URL = baseUri + "getSubjectStudentSchedule";
        return getStudentScheduleRestTemplate(URL, studentId, subjectId);
    }

    public SubjectDto getSubjectRestTemplate(String URL, Long studentId){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<SubjectDto> entity = entityProvider.getDefaultWithTokenFromContext(null, null);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("studentId", studentId);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<SubjectDto>() {}).getBody();
    }

    public ScheduleDtoList getStudentScheduleRestTemplate(String URL, Long studentId){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("studentId", studentId);
        HttpEntity<SubjectDto> entity = entityProvider.getDefaultWithTokenFromContext(null, null);
        ResponseEntity<ScheduleDtoList> response
                = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});

        return new ScheduleDtoList(Objects.requireNonNull(response.getBody()).getSchedules());
    }

    public ScheduleDtoList getStudentScheduleRestTemplate(String URL, Long studentId, Long subjectId){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("studentId", studentId)
                .queryParam("subjectId", subjectId);
        HttpEntity<SubjectDto> entity = entityProvider.getDefaultWithTokenFromContext(null , null);
        ResponseEntity<ScheduleDtoList> response
                = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});

        return new ScheduleDtoList(Objects.requireNonNull(response.getBody()).getSchedules());
    }


    //----------------------------------------------------------//
    //Methods need for teacherTT functionality//

    @Override
    public List<ScheduleDto> getTeacherSchedule(Long teacherId, Optional<Boolean> weekIsOdd) {
        String URL = baseUri + "teacherWeekSchedule";
        return getScheduleRestTemplate(URL, teacherId, weekIsOdd);
    }


    @Override
    public  List<ScheduleDto> getTomorrowTeacherSchedule(Long teacherId, Optional<Boolean> weekIsOdd) {
        String URL = baseUri + "tomorrowTeacherWeekSchedule";
        return getScheduleRestTemplate(URL, teacherId, weekIsOdd);
    }


    @Override
    public List<ScheduleDto> getSubjectTeacherSchedule(List<ScheduleDto> schedules, Long subjectId){

        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUri + "subjectTeacherSchedule")
                .queryParam("subjectId", subjectId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<ScheduleDto>> request = new HttpEntity<>(schedules, headers);
        ResponseEntity<ScheduleDtoList> response
                = restTemplate.postForEntity(builder.toUriString(), request, ScheduleDtoList.class);
        return response.getBody().getSchedules();
    }


    private List<ScheduleDto> getScheduleRestTemplate(String URL, Long teacherId, Optional<Boolean> weekIsOddOptional){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder;

        builder = weekIsOddOptional.map(aBoolean -> UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("teacherId", teacherId)
                .queryParam("weekIsOddOptional", aBoolean))
                .orElseGet(() -> UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("teacherId", teacherId));

        ResponseEntity<ScheduleDtoList> response
                = restTemplate.getForEntity(builder.toUriString(), ScheduleDtoList.class);
        return response.getBody().getSchedules();
    }



    //Redefined hashCode and equals of the ScheduleDto
    @Override
    public Map<ScheduleDto, List<GroupDto>> mapScheduleToGroups(List<ScheduleDto> schedules) {
        Map<ScheduleDto, List<GroupDto>> res = new LinkedHashMap<>();
        for (int i = 0; i < schedules.size(); i++) {
            ScheduleDto sch = schedules.get(i);

            if(res.containsKey(sch)){
                res.get(sch).add(sch.getGroupDto());
            }
            else{
                List<GroupDto> g = new ArrayList<>();
                g.add(sch.getGroupDto());
                res.put(sch, g);
            }
        }
        return res;
    }
    @Override
    public List<ScheduleDto> mapKeysList(Map<ScheduleDto, List<GroupDto>> map){
        return map.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }
}

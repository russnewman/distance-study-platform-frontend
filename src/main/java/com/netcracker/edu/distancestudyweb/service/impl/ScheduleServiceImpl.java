package com.netcracker.edu.distancestudyweb.service.impl;


import com.netcracker.edu.distancestudyweb.dto.GroupDto;
import com.netcracker.edu.distancestudyweb.dto.ScheduleDto;
import com.netcracker.edu.distancestudyweb.dto.wrappers.ScheduleDtoList;
import com.netcracker.edu.distancestudyweb.dto.SubjectDto;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import com.netcracker.edu.distancestudyweb.service.ScheduleService;
import com.netcracker.edu.distancestudyweb.utils.RestRequestConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    final private HttpEntityProvider entityProvider;
    private @Value("${rest.url}") String serverUrl;

    public ScheduleServiceImpl(HttpEntityProvider entityProvider) {
        this.entityProvider = entityProvider;
    }

    @Override
    public List<ScheduleDto> getStudentFullSchedule(Long studentId){
        String url = serverUrl + "/full";
        return getStudentScheduleRestTemplate(url, studentId);
    }

    @Override
    public List<ScheduleDto> getStudentTodaySchedule(Long studentId){
        String url = serverUrl + "/todayStudentSchedule";
        return getStudentScheduleRestTemplate(url, studentId);
    }

    @Override
    public List<ScheduleDto> getStudentTomorrowSchedule(Long studentId) {
        String url = serverUrl + "/tomorrowStudentSchedule";
        return getStudentScheduleRestTemplate(url, studentId);
    }

    @Override
    public SubjectDto getStudentCurrentSubject(Long studentId) {
        String url = serverUrl + "/currentStudentSubject";
        return getSubjectRestTemplate(url, studentId);
    }

    @Override
    public SubjectDto getStudentNextSubject(Long studentId) {
        String url = serverUrl + "/nextStudentSubject";
        return getSubjectRestTemplate(url, studentId);
    }

    @Override
    public List<ScheduleDto> getStudentSubjectSchedule(Long studentId, Long subjectId){
        String url = serverUrl + "/getSubjectStudentSchedule";
        return getStudentScheduleRestTemplate(url, studentId, subjectId);
    }

    public SubjectDto getSubjectRestTemplate(String url, Long studentId){
        RestRequestConstructor<SubjectDto> ctor = new RestRequestConstructor<>(entityProvider);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("studentId", studentId);
        return ctor.getRestTemplate(url, builder);
    }

    public List<ScheduleDto> getStudentScheduleRestTemplate(String url, Long studentId){
        RestRequestConstructor<List<ScheduleDto>> ctor = new RestRequestConstructor<>(entityProvider);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("studentId", studentId);
        return ctor.getRestTemplate(url, builder);
    }

    public List<ScheduleDto> getStudentScheduleRestTemplate(String url, Long studentId, Long subjectId){
        RestRequestConstructor<List<ScheduleDto>> ctor = new RestRequestConstructor<>(entityProvider);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("studentId", studentId)
                .queryParam("subjectId", subjectId);
        return ctor.getRestTemplate(url, builder);
    }


    //----------------------------------------------------------//
    //Methods need for teacherTT functionality//

    @Override
    public List<ScheduleDto> getTeacherSchedule(Long teacherId, Optional<Boolean> weekIsOdd) {
        String url = serverUrl + "/teacherWeekSchedule";
        return getScheduleRestTemplate(url, teacherId, weekIsOdd);
    }


    @Override
    public  List<ScheduleDto> getTomorrowTeacherSchedule(Long teacherId, Optional<Boolean> weekIsOdd) {
        String url = serverUrl + "tomorrowTeacherWeekSchedule";
        return getScheduleRestTemplate(url, teacherId, weekIsOdd);
    }


    @Override
    public List<ScheduleDto> getSubjectTeacherSchedule(List<ScheduleDto> schedules, Long subjectId){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serverUrl + "subjectTeacherSchedule")
                .queryParam("subjectId", subjectId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<ScheduleDto>> request = new HttpEntity<>(schedules, headers);
        ResponseEntity<ScheduleDtoList> response
                = restTemplate.postForEntity(builder.toUriString(), request, ScheduleDtoList.class);
        return response.getBody().getSchedules();
    }


    private List<ScheduleDto> getScheduleRestTemplate(String url, Long teacherId, Optional<Boolean> weekIsOddOptional){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder;

        builder = weekIsOddOptional.map(aBoolean -> UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("teacherId", teacherId)
                .queryParam("weekIsOddOptional", aBoolean))
                .orElseGet(() -> UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("teacherId", teacherId));
        HttpEntity<SubjectDto> entity = entityProvider.getDefaultWithTokenFromContext(null , null);
        ResponseEntity<List<ScheduleDto>> response
                = restTemplate.exchange(
                        builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});
        return response.getBody();
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

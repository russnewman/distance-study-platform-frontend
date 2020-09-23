package com.netcracker.edu.distancestudyweb.service.impl;


import com.netcracker.edu.distancestudyweb.domain.StudentEvent;
import com.netcracker.edu.distancestudyweb.dto.GroupDto;
import com.netcracker.edu.distancestudyweb.dto.ScheduleDto;
import com.netcracker.edu.distancestudyweb.dto.wrappers.ScheduleDtoList;
import com.netcracker.edu.distancestudyweb.dto.SubjectDto;
import com.netcracker.edu.distancestudyweb.dto.wrappers.SubjectDtoList;
import com.netcracker.edu.distancestudyweb.exception.InternalServiceException;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import com.netcracker.edu.distancestudyweb.service.ScheduleService;
import com.netcracker.edu.distancestudyweb.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private @Value("${rest.url}") String serverUrl;
    private RestTemplate restTemplate;
    private HttpEntityProvider entityProvider;

    public ScheduleServiceImpl(RestTemplate restTemplate, HttpEntityProvider entityProvider) {
        this.restTemplate = restTemplate;
        this.entityProvider = entityProvider;
    }


    @Override
    public ScheduleDtoList getStudentFullSchedule(Long studentId){
        String URL = serverUrl + "/full";
        return getStudentScheduleRestTemplate(URL, studentId);
    }

    @Override
    public ScheduleDtoList getStudentTodaySchedule(Long studentId){
        String URL = serverUrl + "/todayStudentSchedule";
        return getStudentScheduleRestTemplate(URL, studentId);
    }

    @Override
    public ScheduleDtoList getStudentTomorrowSchedule(Long studentId) {
        String URL = serverUrl + "/tomorrowStudentSchedule";
        return getStudentScheduleRestTemplate(URL, studentId);
    }

    @Override
    public SubjectDto getStudentCurrentSubject(Long studentId) {
        String URL = serverUrl + "/currentStudentSubject";
        return getSubjectRestTemplate(URL, studentId);
    }

    @Override
    public SubjectDto getStudentNextSubject(Long studentId) {
        String URL = serverUrl + "/nextStudentSubject";
        return getSubjectRestTemplate(URL, studentId);
    }

    @Override
    public ScheduleDtoList getStudentSubjectSchedule(Long studentId, Long subjectId){
        String URL = serverUrl + "/getSubjectStudentSchedule";
        return getStudentScheduleRestTemplate(URL, studentId, subjectId);
    }

    public SubjectDto getSubjectRestTemplate(String URL, Long studentId){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("studentId", studentId);
        return restTemplate.getForObject(builder.toUriString(), SubjectDto.class);
    }

    public ScheduleDtoList getStudentScheduleRestTemplate(String URL, Long studentId){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("studentId", studentId);

        ResponseEntity<ScheduleDtoList> response
                = restTemplate.getForEntity(builder.toUriString(), ScheduleDtoList.class);

        return new ScheduleDtoList(Objects.requireNonNull(response.getBody()).getSchedules());
    }

    public ScheduleDtoList getStudentScheduleRestTemplate(String URL, Long studentId, Long subjectId){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("studentId", studentId)
                .queryParam("subjectId", subjectId);
        ResponseEntity<ScheduleDtoList> response
                = restTemplate.getForEntity(builder.toUriString(), ScheduleDtoList.class);

        return new ScheduleDtoList(Objects.requireNonNull(response.getBody()).getSchedules());
    }


    //----------------------------------------------------------//
    //Methods need for teacherTT functionality//

    @Override
    public List<ScheduleDto> getTeacherSchedule(Long teacherId, Optional<Boolean> weekIsOdd) {

        HttpEntity<?> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);

        String url = serverUrl + "/teacherWeekSchedule";
        UriComponentsBuilder builder;
        builder = weekIsOdd.map(aBoolean -> UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("teacherId", teacherId)
                .queryParam("weekIsOddOptional", aBoolean))
                .orElseGet(() -> UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("teacherId", teacherId));

        ResponseEntity<ScheduleDtoList> restAuthResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, ScheduleDtoList.class);
        return restAuthResponse.getBody().getSchedules();

    }


    @Override
    public  List<ScheduleDto> getTomorrowTeacherSchedule(Long teacherId, Optional<Boolean> weekIsOdd) {

            HttpEntity<?> httpEntity = entityProvider.getDefaultWithTokenFromContext(null, null);
            String url = serverUrl + "/tomorrowTeacherWeekSchedule";
            UriComponentsBuilder builder = weekIsOdd.map(aBoolean -> UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("teacherId", teacherId)
                    .queryParam("weekIsOddOptional", aBoolean))
                    .orElseGet(() -> UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("teacherId", teacherId));

            ResponseEntity<ScheduleDtoList> restAuthResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, ScheduleDtoList.class);
            return restAuthResponse.getBody().getSchedules();

    }


    @Override
    public List<ScheduleDto> getSubjectTeacherSchedule(List<ScheduleDto> schedules, Long subjectId){

        try{
            HttpEntity<List<ScheduleDto>> httpEntity = entityProvider.getDefaultWithTokenFromContext(schedules, null);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("subjectId", subjectId);
            String url = ServiceUtils.injectParamsInUrl(serverUrl + "/subjectTeacherSchedule", parameters);
            ResponseEntity<ScheduleDtoList> restAuthResponse = restTemplate.exchange(url, HttpMethod.POST, httpEntity, ScheduleDtoList.class);
            return restAuthResponse.getBody().getSchedules();
        }
        catch (UnsupportedEncodingException e) {
            throw new InternalServiceException(e);
        }
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

package com.netcracker.edu.distancestudyweb.service.impl;


import com.netcracker.edu.distancestudyweb.dto.*;
import com.netcracker.edu.distancestudyweb.dto.wrappers.ScheduleDtoList;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import com.netcracker.edu.distancestudyweb.service.ScheduleService;
import com.netcracker.edu.distancestudyweb.utils.RestRequestConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.DayOfWeek;
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
    public Map<AbstractMap.SimpleEntry<String, Boolean>, List<ScheduleVDto>> getStudentFullSchedule(Long studentId){
        String url = serverUrl + "/full";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        builder.queryParam("studentId", studentId);
        return mapSchedule(getScheduleVDtos(builder));
    }

    @Override
    public Map<AbstractMap.SimpleEntry<String, Boolean>, List<ScheduleVDto>> getStudentSubjectSchedule(Long studentId, Long subjectId){
        String url = serverUrl + "/getSubjectStudentSchedule";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        builder
                .queryParam("studentId", studentId)
                .queryParam("subjectId", subjectId);
        return mapSchedule(getScheduleVDtos(builder));
    }

    @Override
    public List<ScheduleVDto> getStudentTodaySchedule(Long studentId){
        String url = serverUrl + "/todayStudentSchedule";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        builder.queryParam("studentId", studentId);
        return getScheduleVDtos(builder);
    }

    @Override
    public List<ScheduleVDto> getStudentTomorrowSchedule(Long studentId) {
        String url = serverUrl + "/tomorrowStudentSchedule";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        builder.queryParam("studentId", studentId);
        return getScheduleVDtos(builder);
    }

    private List<ScheduleVDto> getScheduleVDtos(UriComponentsBuilder builder) {
        HttpEntity<List<ScheduleVDto>> entity = entityProvider.getWithTokenFromContext(null, null, null);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<ScheduleVDto>> response
                = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ScheduleVDto>>() {}
        );
        return response.getBody();
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

    private SubjectDto getSubjectRestTemplate(String url, Long studentId){
        RestRequestConstructor<SubjectDto> ctor = new RestRequestConstructor<>(entityProvider);
        MultiValueMap<String, String> params =new LinkedMultiValueMap<>();
        params.add("studentId", studentId.toString());
        return ctor.getRestTemplate(url, params);
    }

    private List<ScheduleVDto> getStudentScheduleRestTemplate(String url, Long studentId){
        RestRequestConstructor<List<ScheduleVDto>> ctor = new RestRequestConstructor<>(entityProvider);
        MultiValueMap<String, String> params =new LinkedMultiValueMap<>();
        params.add("studentId", studentId.toString());
        return ctor.getRestTemplate(url, params);
    }

    private List<ScheduleVDto> getStudentScheduleRestTemplate(String url, Long studentId, Long subjectId){
        RestRequestConstructor<List<ScheduleVDto>> ctor = new RestRequestConstructor<>(entityProvider);
        MultiValueMap<String, String> params =new LinkedMultiValueMap<>();
        params.add("studentId", studentId.toString());
        params.add("subjectId", subjectId.toString());
        return ctor.getRestTemplate(url, params);
    }

    private Map <AbstractMap.SimpleEntry <String, Boolean>, List<ScheduleVDto>> mapSchedule(List<ScheduleVDto> schedules){
        Map <AbstractMap.SimpleEntry <String, Boolean>, List<ScheduleVDto>> mapped = new TreeMap<>(new ScheduleMapComp());
        schedules.forEach((s) -> {
                    var entry = new AbstractMap.SimpleEntry<>(s.getDayName(), s.getWeekIsOdd());
                    if(!mapped.containsKey(entry)) mapped.put(entry, new ArrayList<>());
                    mapped.get(entry).add(s);
                }
        );
        mapped.forEach((key, value) -> value.sort(new ScheduleListComp()));
        return mapped;
    }

    static class ScheduleMapComp implements Comparator<AbstractMap.SimpleEntry <String, Boolean>>{
        @Override
        public int compare(AbstractMap.SimpleEntry<String, Boolean> o1, AbstractMap.SimpleEntry<String, Boolean> o2) {
            if(o1.getValue().equals(o2.getValue())){
                return DayOfWeek.valueOf(o1.getKey()).compareTo(DayOfWeek.valueOf(o2.getKey()));
            }
            return o1.getValue().compareTo(o2.getValue());
        }
    }

    static class ScheduleListComp implements Comparator<ScheduleVDto>{
        @Override
        public int compare(ScheduleVDto o1, ScheduleVDto o2) {
            return o1.getClassTimeDto().getStartTime().compareTo(o2.getClassTimeDto().getStartTime());
        }
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

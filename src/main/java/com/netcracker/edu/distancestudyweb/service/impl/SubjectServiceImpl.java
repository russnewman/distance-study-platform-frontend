package com.netcracker.edu.distancestudyweb.service.impl;


import com.netcracker.edu.distancestudyweb.domain.Subject;
import com.netcracker.edu.distancestudyweb.dto.SubjectDto;
import com.netcracker.edu.distancestudyweb.service.HttpEntityProvider;
import com.netcracker.edu.distancestudyweb.service.SubjectService;
import com.netcracker.edu.distancestudyweb.utils.RestRequestConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    final private HttpEntityProvider entityProvider;
    private @Value("${rest.url}") String serverUrl;

    public  SubjectServiceImpl(HttpEntityProvider entityProvider){
        this.entityProvider = entityProvider;
    }
    @Override
    public List<Subject> getAll() {
        String url = serverUrl + "/allSubjects";
        return getPureSubjectRestTemplate(url);
    }

    @Override
    public List<SubjectDto> getAllSubjects(){
        String url = serverUrl + "/allSubjects";
        return getSubjectRestTemplate(url);
    }

    private List<SubjectDto> getSubjectRestTemplate(String url){
        RestRequestConstructor<List<SubjectDto>> ctor = new RestRequestConstructor<>(entityProvider);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        return ctor.getRestTemplate(url, builder);
    }

    private List<Subject> getPureSubjectRestTemplate(String url){
        RestRequestConstructor<List<Subject>> ctor = new RestRequestConstructor<>(entityProvider);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        return ctor.getRestTemplate(url, builder);
    }

    @Override
    public List<SubjectDto> getSubjectsByTeacherId(Long teacherId) {
        RestRequestConstructor<List<SubjectDto>> ctor = new RestRequestConstructor<>(entityProvider);
        String url = serverUrl + "/subjectsByTeacher";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("teacherId", teacherId);
        return ctor.getRestTemplate(url, builder);
    }
}

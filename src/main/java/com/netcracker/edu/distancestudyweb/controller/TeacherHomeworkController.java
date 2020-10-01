package com.netcracker.edu.distancestudyweb.controller;

import com.netcracker.edu.distancestudyweb.dto.AssignmentDto;
import com.netcracker.edu.distancestudyweb.dto.DatabaseFileDto;
import com.netcracker.edu.distancestudyweb.dto.EventDto;
import com.netcracker.edu.distancestudyweb.dto.EventFormDto;
import com.netcracker.edu.distancestudyweb.payload.Response;
import com.netcracker.edu.distancestudyweb.service.*;
import com.netcracker.edu.distancestudyweb.service.impl.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/teacherHomework")
public class TeacherHomeworkController {

    private final EventService eventUiService;
    private final SubjectService subjectUiService;
    private final GroupService groupService;
    private final AssignmentService assignmentService;
    private final DatabaseFileService databaseFileService;

    private @Value("${rest.url}") String restUrl;
    private @Value("${server.url}") String uiUrl;


    @Autowired
    public TeacherHomeworkController(EventService eventUiService, SubjectService subjectUiService, GroupService groupService, AssignmentService assignmentService, DatabaseFileService databaseFileService) {
        this.eventUiService = eventUiService;
        this.subjectUiService = subjectUiService;
        this.groupService = groupService;
        this.assignmentService = assignmentService;
        this.databaseFileService = databaseFileService;
    }


    @GetMapping
    public String homework(Model model){
        model.addAttribute("teacherId", SecurityUtils.getId());
        return "teacherHomework/main";
    }


    @GetMapping("/addEvent/{subjectName}")
    public String eventAdd(@PathVariable String subjectName,Model model){
        final Long teacherId = SecurityUtils.getId();
        model.addAttribute("teacherId", teacherId);
        model.addAttribute("subjectName", subjectName);
        model.addAttribute("groups", groupService.findGroupsByTeacherAndSubject(teacherId, subjectName));
        return "teacherHomework/addEvent";
    }


    @GetMapping("/getEvents")
    public String getEvents(@RequestParam Optional<String> sortingTypeOptional,
                            @RequestParam Optional<String> subjectNameOptional,
                            Model model){

        final Long teacherId = SecurityUtils.getId();

        String sortingType = sortingTypeOptional.orElse("addSort");
        String subjectName = subjectNameOptional.orElse("all");


        model.addAttribute("events", eventUiService.getEvents(teacherId, sortingType, subjectName));
        model.addAttribute("subjects", subjectUiService.getSubjectsByTeacherId(teacherId));
        model.addAttribute("dateTimeFormatter", DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        model.addAttribute("sortingType", sortingType);
        model.addAttribute("subjectName", subjectName);

        return "teacherHomework/getEvents";
    }



    @GetMapping("/showSubjects")
    public String showSubjects(Model model){

        final Long teacherId = SecurityUtils.getId();
        model.addAttribute("subjects", subjectUiService.getSubjectsByTeacherId(teacherId));
        return "teacherHomework/chooseSubject";
    }


    @GetMapping("/editEvent/{eventId}")
    public String editEvent(@PathVariable Long eventId,
                            @RequestParam("sortingType") String sortingType,
                            @RequestParam("subjectName") String subjectName,
                            Model model){

        final Long teacherId = SecurityUtils.getId();
        EventDto event = eventUiService.getEventById(eventId);
        model.addAttribute("event", event);
        model.addAttribute("teacherId", teacherId);
        model.addAttribute("groups", groupService.findGroupsByTeacherAndSubject(teacherId, event.getSubject().getName()));
        model.addAttribute("dateTimeFormatter", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        model.addAttribute("sortingType", sortingType);
        model.addAttribute("subjectName", subjectName);

        return "teacherHomework/editEvent";
    }


    @PostMapping("/deleteEvent/{eventId}")
    public String deleteEvent(@PathVariable Long eventId,
                              @RequestParam("sortingType") String sortingType,
                              @RequestParam("subjectName") String subjectName){
        eventUiService.deleteEvent(eventId);


        String URL = uiUrl + "teacherHomework/getEvents";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("sortingTypeOptional", sortingType)
                .queryParam("subjectNameOptional", subjectName);

        return "redirect:" +  builder.toUriString();
    }

    @PostMapping("/editEvent/{eventId}")
    public String editEvent(@PathVariable Long eventId,
                            @RequestParam String groupName,
                            @RequestParam String description,
                            @RequestParam String endTime,
                            @RequestParam MultipartFile fileOptional,

                            @RequestParam("sortingType") String sortingType,
                            @RequestParam("subjectName") String subjectName) throws IOException
                                                                            {


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = new Date();
        try {
            endDate = df.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        EventFormDto eventFormDto = new EventFormDto();
        eventFormDto.setGroupName(groupName);
        eventFormDto.setDescription(description);
        eventFormDto.setEndTime(endDate);


        if (!fileOptional.isEmpty()){
            Response response = databaseFileService.saveDatabaseFile(fileOptional);
            DatabaseFileDto databaseFileDto = new DatabaseFileDto();
            databaseFileDto.setId(response.getFileId());
            eventFormDto.setDatabaseFileDto(databaseFileDto);
        }

        eventUiService.editEvent(eventId, eventFormDto);


        String URL = uiUrl + "teacherHomework/getEvents";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("sortingTypeOptional", sortingType)
                .queryParam("subjectNameOptional", subjectName);

        return "redirect:" + builder.toUriString();
    }


    @PostMapping("/addEvent")
    public String eventPostAdd(
            @RequestParam String subjectName,
            @RequestParam String groupName,
            @RequestParam String description,
            @RequestParam String endTime,
            @RequestParam MultipartFile fileOptional) throws IOException {


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = new Date();
        try {
            endDate = df.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        EventFormDto eventFormDto = new EventFormDto();
        eventFormDto.setTeacherId(SecurityUtils.getId());
        eventFormDto.setSubjectName(subjectName);
        eventFormDto.setGroupName(groupName);
        eventFormDto.setDescription(description);
        eventFormDto.setStartTime(new Date());
        eventFormDto.setEndTime(endDate);


        if (!fileOptional.isEmpty()){

            Response response = databaseFileService.saveDatabaseFile(fileOptional);

            DatabaseFileDto databaseFileDto = new DatabaseFileDto();
            databaseFileDto.setId(response.getFileId());
            eventFormDto.setDatabaseFileDto(databaseFileDto);
        }
        eventUiService.saveEventDto(eventFormDto);

        return "redirect:/teacherHomework";
    }



    @GetMapping("/getAssignments/{eventId}")
    public String getAssignments(@PathVariable Long eventId,
                                 @RequestParam Long groupId,
                                 @RequestParam String startDate,
                                 @RequestParam String endDate,
                                 @RequestParam String subjectName, Model model){


        List<List<AssignmentDto>> assignments = assignmentService.getAssignmentsByEvent(eventId, groupId);
        model.addAttribute( "assessedAssignments", assignments.get(0));
        model.addAttribute( "unassessedAssignments", assignments.get(1));
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("subjectName", subjectName);
        model.addAttribute("restUrl", restUrl);

        return "assignments_for_teacher";
    }




    @PostMapping("/saveResponseForAssignment/{assignmentId}")
    public String saveResponseForAssignment(@RequestParam String commentary,
                                            @RequestParam Integer grade,
                                            @RequestParam Long eventId,
                                            @RequestParam Long groupId,

                                            @RequestParam String startDate,
                                            @RequestParam String endDate,
                                            @RequestParam String subjectName,
                                            @PathVariable Long assignmentId){

        AssignmentDto assignment = new AssignmentDto();
        assignment.setId(assignmentId);
        assignment.setCommentary(commentary);
        assignment.setGrade(grade);
        assignmentService.update(assignment);


        String url = uiUrl + "/teacherHomework/getAssignments/" + eventId.toString();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("groupId", groupId)
                .queryParam("startDate", startDate)
                .queryParam("endDate", endDate)
                .queryParam("subjectName", subjectName);

        return "redirect:" + builder.toUriString();
    }


    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId){
        return databaseFileService.downloadFile(fileId);
    }

}

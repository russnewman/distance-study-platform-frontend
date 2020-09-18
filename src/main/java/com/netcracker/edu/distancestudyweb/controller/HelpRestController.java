package com.netcracker.edu.distancestudyweb.controller;

import com.netcracker.edu.distancestudyweb.service.HomeworkService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelpRestController {
    private HomeworkService homeworkService;
    private @Value("${server.url}") String url;

    public HelpRestController(HomeworkService homeworkService) {
        this.homeworkService = homeworkService;
    }

    @GetMapping("/files/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        return homeworkService.downloadFile(fileId);
    }
}

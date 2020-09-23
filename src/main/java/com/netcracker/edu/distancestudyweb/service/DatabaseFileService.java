package com.netcracker.edu.distancestudyweb.service;

import com.netcracker.edu.distancestudyweb.payload.Response;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


public interface DatabaseFileService {
    public Response saveDatabaseFile(MultipartFile multipartFile);
    ResponseEntity<Resource> downloadFile(String fileId);

}

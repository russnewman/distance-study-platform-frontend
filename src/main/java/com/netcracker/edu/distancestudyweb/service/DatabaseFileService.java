package com.netcracker.edu.distancestudyweb.service;

import com.netcracker.edu.distancestudyweb.payload.Response;
import org.springframework.web.multipart.MultipartFile;


public interface DatabaseFileService {
    public Response saveDatabaseFile(MultipartFile multipartFile);
}

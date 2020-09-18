package com.netcracker.edu.distancestudyweb.service;

import org.springframework.web.multipart.MultipartFile;


public interface DatabaseFileUiService {
    public void saveDatabaseFile(MultipartFile multipartFile);
}

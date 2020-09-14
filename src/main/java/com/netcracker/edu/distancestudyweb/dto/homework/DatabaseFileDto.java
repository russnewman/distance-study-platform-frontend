package com.netcracker.edu.distancestudyweb.dto.homework;

import lombok.Data;
import org.springframework.util.MimeType;

@Data
public class DatabaseFileDto {
    private byte[] file;
    private String fileType;
    private String fileName;
}

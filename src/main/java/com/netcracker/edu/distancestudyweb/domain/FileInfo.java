package com.netcracker.edu.distancestudyweb.domain;

import lombok.Data;
import org.springframework.util.MimeType;

@Data
public class FileInfo {
    private byte[] data;
    private String type;
}

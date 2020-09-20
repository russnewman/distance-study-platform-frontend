package com.netcracker.edu.distancestudyweb.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseFileDto {

    private String id;
    private String fileName;
    private String fileType;
//    private byte[] file;

    public DatabaseFileDto(String fileName, String fileType){
        this.fileName = fileName;
        this.fileType = fileType;
//        this.file = file;
    }
}

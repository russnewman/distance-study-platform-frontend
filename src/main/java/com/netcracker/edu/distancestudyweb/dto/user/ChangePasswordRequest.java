package com.netcracker.edu.distancestudyweb.dto.user;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String password;
    private String newPassword;
}

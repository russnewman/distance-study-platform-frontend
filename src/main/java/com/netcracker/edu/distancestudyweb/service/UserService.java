package com.netcracker.edu.distancestudyweb.service;

import com.netcracker.edu.distancestudyweb.domain.User;
import com.netcracker.edu.distancestudyweb.dto.user.ChangePasswordRequest;

public interface UserService {
    User getUserInfo();
    void changePassword(ChangePasswordRequest request);
}

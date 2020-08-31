package com.netcracker.edu.distancestudyweb.service;

import com.netcracker.edu.distancestudyweb.dto.user.GetUserInfoRequest;
import com.netcracker.edu.distancestudyweb.dto.user.GetUserInfoResponse;
import com.netcracker.edu.distancestudyweb.exception.UserNotFoundException;

public interface UserService {
    GetUserInfoResponse getUserInfo(GetUserInfoRequest request) throws UserNotFoundException;
}

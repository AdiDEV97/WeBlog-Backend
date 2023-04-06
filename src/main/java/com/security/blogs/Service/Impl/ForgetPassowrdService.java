package com.security.blogs.Service.Impl;

import com.security.blogs.Payloads.UserDto;

import java.util.List;

public interface ForgetPassowrdService {

    UserDto verifyEmailAndUpdatePassword(UserDto userToUpdate, String userEmail);


}

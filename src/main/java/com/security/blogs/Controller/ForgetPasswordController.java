package com.security.blogs.Controller;

import com.security.blogs.Payloads.UserDto;
import com.security.blogs.Service.Impl.ForgetPassowrdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ForgetPasswordController {

    @Autowired
    private ForgetPassowrdService fps;

    @PutMapping("/verify-email/{userEmail}")
    public ResponseEntity<UserDto> verifyEmailAndUpdatePasswordController(@RequestBody UserDto userToUpdate, @PathVariable("userEmail") String userEmail) {

        UserDto userDto = this.fps.verifyEmailAndUpdatePassword(userToUpdate, userEmail);
        if(userDto == null) {
            return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<UserDto>(userDto, HttpStatus.FOUND);
        }
    }
}

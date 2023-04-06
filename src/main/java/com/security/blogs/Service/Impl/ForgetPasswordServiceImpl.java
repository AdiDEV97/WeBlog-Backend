package com.security.blogs.Service.Impl;

import com.security.blogs.Dao.UserRepo;
import com.security.blogs.Exceptions.EmailNotFoundException;
import com.security.blogs.Exceptions.ResourceNotFoundException;
import com.security.blogs.Model.User;
import com.security.blogs.Payloads.GmailSend;
import com.security.blogs.Payloads.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ForgetPasswordServiceImpl implements ForgetPassowrdService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto verifyEmailAndUpdatePassword(UserDto userToUpdate, String userEmail) {

        User user = this.userRepo.findByEmail(userEmail).orElseThrow(() -> new EmailNotFoundException("User", "userEmail", userEmail));

        user.setPassword(this.passwordEncoder.encode(userToUpdate.getPassword()));

        this.userRepo.save(user);

        System.out.println("New Password is Updated!!");

        return this.modelMapper.map(user, UserDto.class);
    }


}

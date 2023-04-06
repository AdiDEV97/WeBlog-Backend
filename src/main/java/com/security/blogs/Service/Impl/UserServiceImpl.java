package com.security.blogs.Service.Impl;

import com.security.blogs.Config.AppConstants;
import com.security.blogs.Dao.RoleRepo;
import com.security.blogs.Dao.UserRepo;
import com.security.blogs.Exceptions.ResourceNotFoundException;
import com.security.blogs.Model.Role;
import com.security.blogs.Model.User;
import com.security.blogs.Payloads.UserDto;
import com.security.blogs.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public List<UserDto> getAllUsers() {

        List<User> allUsers = userRepo.findAll();

        List<UserDto> allUsersDto = allUsers.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());

        return allUsersDto;
    }

    @Override
    public UserDto getUserById(int id) {

        User userById = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

        UserDto userByIdDto = this.modelMapper.map(userById, UserDto.class);

        return userByIdDto;
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = this.modelMapper.map(userDto, User.class);

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        User newUser = this.userRepo.save(user);

        return this.modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, int id) {

        User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setProfileImage(userDto.getProfileImage());
        //user.setRole(userDto.getRole());

        User savedUser = this.userRepo.save(user);

        return this.modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public void deleteUser(int id) {

        User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

        this.userRepo.delete(user);
    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {

        User user = this.modelMapper.map(userDto, User.class);

        //Encoding the Password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        // Setting Default User Profile Image
        user.setProfileImage("DefaultProfileImage.png");

        //Get Role for the user
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

        user.getRoles().add(role);

        User savedUser = this.userRepo.save(user);

        return this.modelMapper.map(savedUser, UserDto.class);
    }

}

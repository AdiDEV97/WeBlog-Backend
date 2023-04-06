package com.security.blogs.Service;

import com.security.blogs.Payloads.UserDto;

import java.util.List;

public interface UserService {

    // Get all
    List<UserDto> getAllUsers();

    // Get By Id
    UserDto getUserById(int id);

    // Create New User
    UserDto createUser(UserDto userDto);

    // Update User By Id
    UserDto updateUser(UserDto userDto, int id);

    // Delete User
    void deleteUser(int id);

    UserDto registerNewUser(UserDto userDto);

}

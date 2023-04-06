package com.security.blogs.Controller;

import com.security.blogs.Exceptions.ResourceNotFoundException;
import com.security.blogs.Model.ApiResponse;
import com.security.blogs.Payloads.UserDto;
import com.security.blogs.Service.ImageService;
import com.security.blogs.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userServ;

    @Autowired
    private ImageService imageServ;

    @Value("${blog.image}")
    private String path;

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDto>> getAll() {

        List<UserDto> allUsers = this.userServ.getAllUsers();

        if(allUsers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            return ResponseEntity.of(Optional.of(allUsers));
        }

    }

    @GetMapping("/userId/{id}")
    public ResponseEntity<UserDto> getById(@Valid @PathVariable int id) {
        UserDto user = this.userServ.getUserById(id);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else {
            return ResponseEntity.of(Optional.of(user));
        }
    }

    @PostMapping("/newUser")
    public ResponseEntity<ApiResponse> addNew(@Valid @RequestBody UserDto userDto) {
        try {
            UserDto newUser = this.userServ.createUser(userDto);
            return new ResponseEntity<ApiResponse>(new ApiResponse(String.format("%s you are registered successfully!!", userDto.getName()), true), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<ApiResponse>(new ApiResponse("Error in registration!! Please check.", false), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasRole('NORMAL')")
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUserById(@Valid @RequestBody UserDto userDto, @PathVariable int id) {
        try {
            UserDto userToUpdate = this.userServ.updateUser(userDto, id);
            return ResponseEntity.of(Optional.of(userToUpdate));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //ADMIN ROLE
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@Valid @PathVariable int id) {
        try {
            this.userServ.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    // Upload User Profile Image
    @PostMapping("/profile-image/upload/{id}")
    public ResponseEntity<UserDto> uploadProfileImage(@RequestParam("profileImage")MultipartFile profileImage, @PathVariable("id") int id) throws IOException {
        System.out.println("Image Path - " + path);
        System.out.println("Image - " + profileImage);
        System.out.println("UserId - " + id);

        // Get User
        UserDto userDto = this.userServ.getUserById(id);
        // Get Image Name
        String profileImageName = this.imageServ.uploadingImage(path, profileImage);
        System.out.println("ImageName C - " + profileImageName);
        // Set Image in the Dto
        userDto.setProfileImage(profileImageName);

        // Update the User
        UserDto updatedUserWithProfileImage = this.userServ.updateUser(userDto, id);

        return new ResponseEntity<UserDto>(updatedUserWithProfileImage, HttpStatus.OK);
    }

    // Get (Stream) Image from Database
    @GetMapping(value = "/profile-image/{profileImageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getProfileImageUrl(@PathVariable("profileImageName") String profileImageName, HttpServletResponse httpServletResponse) throws IOException {
        InputStream resource = this.imageServ.getImage(path, profileImageName); // Stream Created
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE); // Set type of data that we want to get
        StreamUtils.copy(resource, httpServletResponse.getOutputStream()); // Copy the stream data to the response

    }
}

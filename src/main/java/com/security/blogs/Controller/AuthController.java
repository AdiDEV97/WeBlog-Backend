package com.security.blogs.Controller;

import com.security.blogs.Payloads.JwtAuthRequest;
import com.security.blogs.Payloads.JwtAuthResponse;
import com.security.blogs.Payloads.UserDto;
import com.security.blogs.Security.CustomUserDetailsService;
import com.security.blogs.Service.UserService;
import com.security.blogs.Util.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws Exception {

        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword()));
        }catch (UsernameNotFoundException e) {
            e.printStackTrace();
            System.out.println("Bad Credentials!!");
            throw new Exception("Invalid Username or Password!!");
        }catch (BadCredentialsException e) {
            e.printStackTrace();
            System.out.println("Bad Credentials!!");
            throw new Exception("Invalid Username or Password!!");
        }

        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());

        String token = this.jwtUtil.generateToken(userDetails);

        System.out.println("Generated Token - " + token);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();

        jwtAuthResponse.setToken(token);

        jwtAuthResponse.setUserDto(this.modelMapper.map(userDetails, UserDto.class));

        return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.OK);

    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        UserDto registeredUser = this.userService.registerNewUser(userDto);
        return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
    }

}

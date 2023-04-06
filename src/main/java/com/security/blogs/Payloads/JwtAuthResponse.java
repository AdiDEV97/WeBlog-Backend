package com.security.blogs.Payloads;

import lombok.Data;

@Data
public class JwtAuthResponse {

    private String token;

    private UserDto userDto; // This field is used to carry the details of the user who LoggedIn

}

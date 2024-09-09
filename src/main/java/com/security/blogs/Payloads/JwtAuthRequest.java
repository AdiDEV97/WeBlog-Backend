package com.security.blogs.Payloads;

import lombok.Data;

// Jwt Auth Request Class

@Data
public class JwtAuthRequest {

    private String username;

    private String password;
}

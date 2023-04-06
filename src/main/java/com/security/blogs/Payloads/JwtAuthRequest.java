package com.security.blogs.Payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {

    private String username;

    private String password;
}

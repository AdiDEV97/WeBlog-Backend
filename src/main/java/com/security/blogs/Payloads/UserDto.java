package com.security.blogs.Payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.security.blogs.Model.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto {

    private int id;

    private String name;

    private String email;

    private String password;

    private String profileImage;

    // This field is used to get the role details of the user with the user
    private Set<RoleDto> roles = new HashSet<>();


    // To ignore the getting password in the UI
    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    // To accept the setting the password
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

}

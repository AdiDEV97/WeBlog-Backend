package com.security.blogs.Exceptions;

import lombok.Data;

@Data
public class EmailNotFoundException extends RuntimeException{

    private String resourceName;

    private String fieldName;

    private String fieldValue;

    public EmailNotFoundException(String resourceName, String fieldName, String fieldValue) {

        super(String.format("The %s is not found %s : %s", resourceName, fieldName, fieldValue));

        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}

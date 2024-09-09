package com.security.blogs.Payloads;

import lombok.Data;

// CategoryDto Class

@Data
public class CategoryDto {

    private int categoryId;

    private String category_title;

    private String category_description;
}

package com.security.blogs.Payloads;

import lombok.Data;

@Data
public class CategoryDto {

    private int categoryId;

    private String category_title;

    private String category_description;
}

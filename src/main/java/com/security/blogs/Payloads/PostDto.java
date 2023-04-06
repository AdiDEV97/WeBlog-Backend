package com.security.blogs.Payloads;

import com.security.blogs.Model.Category;
import com.security.blogs.Model.Comment;
import com.security.blogs.Model.User;
import lombok.Data;

import java.util.*;

@Data
public class PostDto {

    private int post_id;

    private String post_title;

    private String post_content;

    private String image;

    private Date added_date;

    private UserDto user;

    private CategoryDto category;

    private Set<CommentDto> comments = new HashSet<>();

}

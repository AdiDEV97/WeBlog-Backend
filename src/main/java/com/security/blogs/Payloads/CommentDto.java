package com.security.blogs.Payloads;

import lombok.Data;
import java.util.Date;

@Data
public class CommentDto {

    private int commentId;

    private String comment_content;

    private Date commentAddedDate;

    private UserDto user;

}

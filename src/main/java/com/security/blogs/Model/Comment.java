package com.security.blogs.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId")
    private int commentId;

    @Column(name="commentContent")
    @Size(min=1, max=1000000000, message = "Comment text limit exceeded!! Should be under 1000000000")
    private String comment_content;

    @Column(name = "commentAddedDate")
    private Date commentAddedDate;

    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts posts;
}

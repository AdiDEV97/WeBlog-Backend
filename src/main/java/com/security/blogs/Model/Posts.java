package com.security.blogs.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

@Entity(name = "posts_table")
@Data
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int post_id;

    @NotEmpty(message = "Post title should not be empty!!")
    @Column(name = "title")
    @Size(min=3, max=100, message = "The Category should contain between 3 to 100 characters!!")
    private String post_title;

    @NotEmpty(message = "Post content should not be empty!!")
    @Column(name = "post_content", length = 1000000000)
    @Size(min=3, max=1000000000, message = "The Content should contain between 3 to 100000 characters!!")
    private String post_content;

    @Column(name = "image")
    private String image;

    @Column(name = "added_date")
    private Date added_date;

    //@JsonBackReference
    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id") // NOT NECESSARY
    private User user;

    //@JsonBackReference
    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id") // NOT NECESSARY
    private Category category;

    //For Comment Section
    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private Set<Comment> comment = new HashSet<>();

}

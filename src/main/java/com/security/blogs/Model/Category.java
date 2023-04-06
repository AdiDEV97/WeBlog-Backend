package com.security.blogs.Model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "category_table")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "categoryId")
    private int categoryId;

    @NotEmpty(message = "Category title should not be empty!!")
    @Column(name = "category_title")
    @Size(min=3, max=100, message = "The Category should contain between 3 to 100 characters!!")
    private String category_title;

    @NotEmpty(message = "Category description should not be empty!!")
    @Column(name = "category_description")
    @Size(min=3, max=200, message = "The Description should contain between 3 to 200 characters!!")
    private String category_description;

    //@JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Posts> posts = new ArrayList<>();


}

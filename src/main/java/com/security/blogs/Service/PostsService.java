package com.security.blogs.Service;

import com.security.blogs.Model.Category;
import com.security.blogs.Model.Posts;
import com.security.blogs.Model.User;
import com.security.blogs.Payloads.PostDto;
import com.security.blogs.Payloads.PostPaginationResponse;

import java.util.List;

public interface PostsService {

    // Get All Posts
    PostPaginationResponse getAllPosts(int pageNumber, int pageSize);

    // Get Single Post by Id
    PostDto postById(int postId);

    // Create New Post
    PostDto createPost(PostDto postDto, int userId, int category_id);

    // Update Post by Id
    PostDto updatePost(PostDto postDto, int postId);

    // Delete Post by Id
    void deletePost(int postId);

    // Get all Posts by User
    List<PostDto> getPostsByUser(int userId);

    // Get all Posts by Category
    List<PostDto> getPostsByCategory(int categoryId);

    // Get all Posts By User and Category
    List<PostDto> getPostByUserAndCategory(int userId, int categoryId);
}

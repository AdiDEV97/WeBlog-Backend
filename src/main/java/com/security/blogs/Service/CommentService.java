package com.security.blogs.Service;

import com.security.blogs.Model.Comment;
import com.security.blogs.Model.Posts;
import com.security.blogs.Payloads.CommentDto;

import java.util.List;

public interface CommentService {

    // Get all Comments
    List<CommentDto> getAllComments();

    // Get all Comments By Post
    List<CommentDto> getAllCommentByPost(int post_id);

    // Get Comment By Id
    CommentDto getCommentById(int id);

    // Create Comment By User and Post
    CommentDto createCommentByPosts(CommentDto commentDto, int user_id, int post_id);

    // Update Comment
    CommentDto updateComment(CommentDto commentDto, int commentId);

    // Delete Comment
    void deleteCommentById(int commentId);


}

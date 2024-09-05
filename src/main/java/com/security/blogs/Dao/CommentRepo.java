package com.security.blogs.Dao;

import com.security.blogs.Model.Comment;
import com.security.blogs.Model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Comment Repository

public interface CommentRepo extends JpaRepository<Comment, Integer> {

    List<Comment> findByPosts(Posts posts);

}

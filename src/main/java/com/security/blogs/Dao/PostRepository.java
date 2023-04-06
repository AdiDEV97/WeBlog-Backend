package com.security.blogs.Dao;

import com.security.blogs.Model.Category;
import com.security.blogs.Model.Posts;
import com.security.blogs.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Posts, Integer> {

    List<Posts> findByUser(User user);
    List<Posts> findByCategory(Category category);

    List<Posts> findByUserAndCategory(User user, Category category);
}

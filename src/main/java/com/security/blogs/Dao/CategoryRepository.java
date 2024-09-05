package com.security.blogs.Dao;

import com.security.blogs.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

// Category Repository

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}

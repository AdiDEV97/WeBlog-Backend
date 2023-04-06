package com.security.blogs.Service;

import com.security.blogs.Model.Category;
import com.security.blogs.Payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    public List<CategoryDto> allCatagories();

    public CategoryDto categoryById(int categoryId);

    public CategoryDto newCategory(CategoryDto categoryDto);

    public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId);

    public void deleteCategory(int categoryId);

}

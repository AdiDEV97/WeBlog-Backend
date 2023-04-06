package com.security.blogs.Service.Impl;

import com.security.blogs.Dao.CategoryRepository;
import com.security.blogs.Exceptions.ResourceNotFoundException;
import com.security.blogs.Model.Category;
import com.security.blogs.Payloads.CategoryDto;
import com.security.blogs.Service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepo;

    @Override
    public List<CategoryDto> allCatagories() {
        List<Category> all_category = categoryRepo.findAll();
        List<CategoryDto> all_categoryDto = all_category.stream().map(category -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        return all_categoryDto;
    }

    @Override
    public CategoryDto categoryById(int categoryId) {
        Category oneCategory = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        CategoryDto oneCategoryDto = this.modelMapper.map(oneCategory, CategoryDto.class);
        return oneCategoryDto;
    }

    @Override
    public CategoryDto newCategory(CategoryDto categoryDto) {
        Category new_category = this.modelMapper.map(categoryDto, Category.class);
        Category new_category_added = this.categoryRepo.save(new_category);
        return this.modelMapper.map(new_category_added, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
        Category update_category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        update_category.setCategory_title(categoryDto.getCategory_title());
        update_category.setCategory_description(categoryDto.getCategory_description());

        Category updated_category_saved = this.categoryRepo.save(update_category);

        return this.modelMapper.map(updated_category_saved, CategoryDto.class);
    }

    @Override
    public void deleteCategory(int categoryId) {
        categoryRepo.deleteById(categoryId);
    }
}

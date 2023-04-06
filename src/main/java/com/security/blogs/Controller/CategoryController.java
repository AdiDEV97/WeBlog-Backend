package com.security.blogs.Controller;

import com.security.blogs.Model.ApiResponse;
import com.security.blogs.Model.Category;
import com.security.blogs.Payloads.CategoryDto;
import com.security.blogs.Service.CategoryService;
import com.security.blogs.Service.Impl.CategoryServiceImpl;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryServ;

    @GetMapping("/all-categories")
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        List<CategoryDto> allCategory = categoryServ.allCatagories();

        if(allCategory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            return ResponseEntity.of(Optional.of(allCategory));
        }
    }


    @GetMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@Valid @PathVariable("categoryId") int categoryId) {
        CategoryDto categoryDto = categoryServ.categoryById(categoryId);

        if(categoryDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else {
            return ResponseEntity.of(Optional.of(categoryDto));
        }
    }


    @PostMapping("/new-category")
    public ResponseEntity<CategoryDto> addNewCategory(@Valid @RequestBody CategoryDto categoryDto) {
        try {
            CategoryDto newCategoryDto = categoryServ.newCategory(categoryDto);
            //return ResponseEntity.of(Optional.of(newCategoryDto));
            return new ResponseEntity<CategoryDto>(newCategoryDto, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/update-category/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("categoryId") int categoryId) {
        try{
            CategoryDto categoryDto_to_update = categoryServ.updateCategory(categoryDto, categoryId);
            return ResponseEntity.of(Optional.of(categoryDto_to_update));
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<CategoryDto>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete-category/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@Valid @PathVariable("categoryId") int categoryId) {
        try{
            categoryServ.deleteCategory(categoryId);
            return new ResponseEntity<ApiResponse>(new ApiResponse("Category with id - " + categoryId + " is deleted successfully", true), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



}

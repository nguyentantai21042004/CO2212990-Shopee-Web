package com.example.demo.services.category;

import com.example.demo.dtos.CategoryDTO;
import com.example.demo.models.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategory();
    Category insertNewCategory(CategoryDTO categoryDTO) throws Exception;
    Category getCategoryById(String id) throws Exception;
    Category createCategoryImage(String id, String imageUrl) throws Exception;
}

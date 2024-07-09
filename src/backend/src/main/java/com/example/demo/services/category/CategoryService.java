package com.example.demo.services.category;

import com.example.demo.dtos.CategoryDTO;
import com.example.demo.exceptions.DuplicateKeyException;
import com.example.demo.models.Category;
import com.example.demo.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category insertNewCategory(CategoryDTO categoryDTO) throws Exception {
        Optional<Category> category = categoryRepository.findByName(categoryDTO.getName());
        if(category.isPresent()){
            throw new DuplicateKeyException("This Category has already exits");
        }

        Category newCategory = Category.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .parentId(categoryDTO.getParentId())
                .status(categoryDTO.getStatus())
                .build();

        return categoryRepository.save(newCategory);
    }
}

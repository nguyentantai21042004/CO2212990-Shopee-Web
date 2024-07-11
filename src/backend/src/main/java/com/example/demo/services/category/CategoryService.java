package com.example.demo.services.category;

import com.example.demo.dtos.CategoryDTO;
import com.example.demo.exceptions.DataNotFoundException;
import com.example.demo.exceptions.DuplicateKeyException;
import com.example.demo.models.Category;
import com.example.demo.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
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
                .imageUrl("")
                .build();

        return categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(String id) throws Exception {
        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            throw new Exception("Invalid ObjectId format");
        }

        Optional<Category> optionalCategory = categoryRepository.findById(objectId);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        } else {
            throw new DataNotFoundException("Category not found");
        }
    }

    @Override
    public Category createCategoryImage(String id, String imageUrl) throws Exception {
        Category existingCategory = getCategoryById(id);
        existingCategory.setImageUrl(imageUrl);

        return categoryRepository.save(existingCategory);
    }
}

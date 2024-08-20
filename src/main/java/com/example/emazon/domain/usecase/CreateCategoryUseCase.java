package com.example.emazon.domain.usecase;

import com.example.emazon.domain.model.CategoryEntity;
import com.example.emazon.domain.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCategoryUseCase {

    private final CategoryRepository categoryRepository;

    public CreateCategoryUseCase(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public CategoryEntity createCategory(CategoryEntity categoryEntity) {
        if (categoryRepository.findByName(categoryEntity.getName()).isPresent()) {
            throw new RuntimeException("Category already exists");
        }
        return categoryRepository.save(categoryEntity);
    }
}

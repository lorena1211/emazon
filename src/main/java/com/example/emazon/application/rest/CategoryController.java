package com.example.emazon.application.rest;

import com.example.emazon.application.dto.CategoryDto;
import com.example.emazon.domain.model.CategoryEntity;
import com.example.emazon.domain.usecase.CreateCategoryUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;

    public CategoryController(CreateCategoryUseCase createCategoryUseCase){
        this.createCategoryUseCase = createCategoryUseCase;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryEntity newCategory = new CategoryEntity(null, categoryDto.getName(), categoryDto.getDescription());
        CategoryEntity createdCategory = createCategoryUseCase.createCategory(newCategory);
        categoryDto.setId(createdCategory.getId());
        return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }
}

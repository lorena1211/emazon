package com.example.emazon.application.rest;

import com.example.emazon.application.dto.CategoryDto;
import com.example.emazon.application.dto.CategoryFilterDto;
import com.example.emazon.domain.model.CategoryEntity;
import com.example.emazon.domain.usecase.CreateCategoryUseCase;
import com.example.emazon.domain.usecase.ListCategoriesUseCase;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final ListCategoriesUseCase listCategoriesUseCase;

    public CategoryController(CreateCategoryUseCase createCategoryUseCase,
                              ListCategoriesUseCase listCategoriesUseCase){
        this.createCategoryUseCase = createCategoryUseCase;
        this.listCategoriesUseCase = listCategoriesUseCase;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryEntity newCategory = new CategoryEntity(null, categoryDto.getName(), categoryDto.getDescription());
        CategoryEntity createdCategory = createCategoryUseCase.createCategory(newCategory);
        categoryDto.setId(createdCategory.getId());
        return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories(CategoryFilterDto categoryFilterDto) {
        Page<CategoryEntity> categoriesPage = listCategoriesUseCase.listCategories(
                categoryFilterDto.getPageNumber(),
                categoryFilterDto.getPageSize(),
                categoryFilterDto.getSortBy(),
                categoryFilterDto.getSortDirection()
        );

        List<CategoryDto> categories = categoriesPage.getContent().stream()
                .map(category -> new CategoryDto(category.getId(), category.getName(),
                        category.getDescription()))
                .collect(Collectors.toList());

        return  ResponseEntity.ok().body(categories);
    }
}

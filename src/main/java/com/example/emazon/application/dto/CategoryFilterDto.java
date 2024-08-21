package com.example.emazon.application.dto;

import lombok.Data;

@Data
public class CategoryFilterDto {
    private int pageNumber = 0;
    private int pageSize = 10;
    private String sortBy = "name";
    private String sortDirection = "asc";
}

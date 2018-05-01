package com.spices.api.converter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.spices.api.dto.CategoryCreationRequestDto;
import com.spices.api.dto.CategoryRequestDto;
import com.spices.domain.Category;

public class CategoryCreationRequestToCategoryConverter {

    public List<Category> convert(CategoryCreationRequestDto categoryCreationRequestDto) {
        return categoryCreationRequestDto.getCategories().stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private Category convert(CategoryRequestDto categoryRequestDto) {
        return new Category(
                null,
                categoryRequestDto.getParentCategoryId(),
                categoryRequestDto.getName(),
                categoryRequestDto.getDescription(),
                Collections.emptyList()
        );
    }
}

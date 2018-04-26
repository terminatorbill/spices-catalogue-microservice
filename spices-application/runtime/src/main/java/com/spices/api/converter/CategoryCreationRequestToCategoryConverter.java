package com.spices.api.converter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.spices.api.dto.CategoryCreationRequestDto;
import com.spices.api.dto.CategoryRequestDto;
import com.spices.api.dto.CategoryUpdateRequestDto;
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

    //TODO: Extract to different converter
    public List<Category> convert(List<CategoryUpdateRequestDto> categoryUpdateRequestDtos) {
        return categoryUpdateRequestDtos.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    //TODO: Refactor the parentCategoryId to has a value other than null
    private Category convert(CategoryUpdateRequestDto categoryUpdateRequestDto) {
        return new Category(
                categoryUpdateRequestDto.getId(),
                null,
                categoryUpdateRequestDto.getName(),
                categoryUpdateRequestDto.getDescription(),
                Collections.emptyList()
        );
    }
}

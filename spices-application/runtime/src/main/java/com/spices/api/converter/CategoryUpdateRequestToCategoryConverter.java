package com.spices.api.converter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.spices.api.dto.CategoryUpdateRequestDto;
import com.spices.domain.Category;

public class CategoryUpdateRequestToCategoryConverter {
    public List<Category> convert(List<CategoryUpdateRequestDto> categoryUpdateRequestDtos) {
        return categoryUpdateRequestDtos.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

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

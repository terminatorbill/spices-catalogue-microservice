package com.spices.api.converter;

import java.util.Collections;

import com.spices.api.dto.CategoryResponseDto;
import com.spices.domain.Category;

public class CategoryToCategoryResponseDtoConverter {

    public CategoryResponseDto convert(Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getParentCategoryId(),
                category.getName(),
                category.getDescription()
        );
    }
}

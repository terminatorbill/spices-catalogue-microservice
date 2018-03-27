package com.spices.api.converter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.spices.api.dto.CategoryCreationRequestDto;
import com.spices.domain.Category;

public class CategoryCreationRequestToCategoryConverter {

    public Category convert(CategoryCreationRequestDto categoryCreationRequestDto) {
        return new Category(
            null,
            categoryCreationRequestDto.getParentCategoryId(),
            categoryCreationRequestDto.getName(),
            categoryCreationRequestDto.getDescription(),
            Collections.emptyList(),
            getSubCategories(categoryCreationRequestDto.getSubCategories())
        );
    }

    private List<Category> getSubCategories(List<CategoryCreationRequestDto> subCategories) {
        if (subCategories == null || subCategories.isEmpty()) {
            return Collections.emptyList();
        }
        return subCategories.stream()
            .map(this::convert)
            .collect(Collectors.toList());
    }
}

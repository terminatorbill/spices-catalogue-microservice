package com.spices.service;

import java.util.List;

import com.spices.api.dto.CategoryResponseDto;
import com.spices.domain.Category;

public interface CategoryService {

    void createCategories(Category category);

    void updateCategories(List<Category> categories);

    List<CategoryResponseDto> retrieveCategories();

    void deleteCategories(List<Long> categoryIds);
}

package com.spices.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.spices.domain.Category;

public interface CategoryRepositoryFacade {
    void createCategory(Category category);
    Optional<String> checkAndReturnAnyExistingCategory(Category category);

    boolean checkIfCategoryExists(Long categoryId);

    void updateCategories(List<Category> categories);

    List<Category> getCategories();

    boolean checkIfAnyCategoryHasSubCategories(List<Long> categoryIds);

    void deleteCategories(List<Long> categoryIds);
}

package com.spices.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.spices.domain.Category;

public interface CategoryRepositoryFacade {
    void createCategories(List<Category> categories);

    Optional<String> checkAndReturnAnyExistingCategory(List<Category> categories);

    Optional<Long> isThereACategoryThatDoesNotExist(List<Long> categories);

    boolean checkIfCategoryExists(Long categoryId);

    void updateCategories(List<Category> categories);

    List<Category> getCategories();

    boolean checkIfAnyCategoryHasSubCategories(List<Long> categoryIds);

    void deleteCategories(List<Long> categoryIds);
}

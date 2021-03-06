package com.spices.persistence.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.spices.domain.Category;
import com.spices.persistence.model.CategoryEntity;

public interface CategoryRepository {
    void createCategory(Category category, EntityManager entityManager);

    boolean checkIfCategoryExists(String name, EntityManager entityManager);

    boolean checkIfCategoryExists(Long categoryId, EntityManager entityManager);

    void updateCategory(Category category, EntityManager entityManager);

    List<CategoryEntity> getCategories(EntityManager entityManager);

    CategoryEntity getCategory(Long categoryId, EntityManager entityManager);

    boolean checkIfCategoryHasSubCategories(Long categoryId, EntityManager entityManager);

    void deleteCategories(List<Long> categoryIds, EntityManager entityManager);
}

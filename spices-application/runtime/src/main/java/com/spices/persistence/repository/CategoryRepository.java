package com.spices.persistence.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.spices.domain.Category;
import com.spices.persistence.model.CategoryEntity;

public interface CategoryRepository {
    void createCategory(Category category, EntityManager entityManager);

    boolean checkIfCategoryExists(String name, EntityManager entityManager);

    void updateCategory(Category category, EntityManager entityManager);

    List<CategoryEntity> getCategories(EntityManager entityManager);
}

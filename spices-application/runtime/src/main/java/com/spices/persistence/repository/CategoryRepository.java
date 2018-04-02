package com.spices.persistence.repository;

import javax.persistence.EntityManager;

import com.spices.domain.Category;

public interface CategoryRepository {
    void createCategory(Category category, EntityManager entityManager);

    boolean checkIfCategoryExists(String name, EntityManager entityManager);
}

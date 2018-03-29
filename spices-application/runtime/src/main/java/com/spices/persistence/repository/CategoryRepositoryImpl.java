package com.spices.persistence.repository;

import javax.persistence.EntityManager;

import com.spices.domain.Category;
import com.spices.persistence.model.CategoryEntity;

public class CategoryRepositoryImpl implements CategoryRepository {

    @Override
    public void createCategory(Category category, EntityManager entityManager) {

        CategoryEntity parentCategoryEntity = createCategoryEntity(category, null);

        category.getSubCategories().forEach(subcategory -> {
            CategoryEntity subCategoryEntity = createCategoryEntity(subcategory, parentCategoryEntity);
            entityManager.persist(subCategoryEntity);
        });
    }

    @Override
    public boolean checkIfCategoryExists(String name, EntityManager entityManager) {
        int results = entityManager.createQuery("SELECT COUNT(c) FROM CategoryEntity c WHERE c.categoryName = :name", Integer.class)
            .setParameter("name", name)
            .getSingleResult();
        return results != 0;
    }

    private CategoryEntity createCategoryEntity(Category category, CategoryEntity parentCategory) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryDescription(category.getDescription());
        categoryEntity.setCategoryName(category.getName());
        categoryEntity.setParentCategory(parentCategory);

        return categoryEntity;
    }
}

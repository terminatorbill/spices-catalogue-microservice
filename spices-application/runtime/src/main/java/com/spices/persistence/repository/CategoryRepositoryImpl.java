package com.spices.persistence.repository;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.spices.domain.Category;
import com.spices.persistence.model.CategoryEntity;

public class CategoryRepositoryImpl implements CategoryRepository {

    private final Provider<EntityManager> entityManagerProvider;

    @Inject
    CategoryRepositoryImpl(Provider<EntityManager> entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    @Override
    public void createCategory(Category category) {
        EntityManager entityManager = entityManagerProvider.get();

        CategoryEntity parentCategoryEntity = createCategoryEntity(category, null);

        category.getSubCategories().forEach(subcategory -> {
            CategoryEntity subCategoryEntity = createCategoryEntity(subcategory, parentCategoryEntity);
            entityManager.persist(subCategoryEntity);
        });
    }

    private CategoryEntity createCategoryEntity(Category category, CategoryEntity parentCategory) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryDescription(category.getDescription());
        categoryEntity.setCategoryName(category.getName());
        categoryEntity.setParentCategory(parentCategory);

        return categoryEntity;
    }
}

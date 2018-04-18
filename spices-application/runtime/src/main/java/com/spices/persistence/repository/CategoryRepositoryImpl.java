package com.spices.persistence.repository;

import javax.persistence.EntityManager;

import com.spices.domain.Category;
import com.spices.persistence.model.CategoryEntity;

public class CategoryRepositoryImpl implements CategoryRepository {

    @Override
    public void createCategory(Category category, EntityManager entityManager) {
        CategoryEntity parentCategoryEntity = createCategoryEntity(category, null);
        persistCategories(category, parentCategoryEntity, entityManager);
    }

    @Override
    public boolean checkIfCategoryExists(String name, EntityManager entityManager) {
        long results = entityManager.createQuery("SELECT COUNT(c) FROM CategoryEntity c WHERE c.categoryName = :name", Long.class)
            .setParameter("name", name)
            .getSingleResult();
        return results != 0;
    }

    @Override
    public void updateCategory(Category category, EntityManager entityManager) {
        CategoryEntity categoryEntity = createCategoryEntity(category, null);
        entityManager.persist(categoryEntity);
    }

    private void persistCategories(Category category, CategoryEntity parentCategoryEntity, EntityManager entityManager) {
        entityManager.persist(parentCategoryEntity);
        persistSubCategoriesIfAny(category, parentCategoryEntity, entityManager);
    }

    private void persistSubCategoriesIfAny(Category category, CategoryEntity parentCategoryEntity, EntityManager entityManager) {
        for (int i = 0; i < category.getSubCategories().size(); i++) {
            Category subcategory = category.getSubCategories().get(i);
            CategoryEntity subCategoryEntity = createCategoryEntity(subcategory, parentCategoryEntity);
            entityManager.persist(subCategoryEntity);
            persistSubCategoriesIfAny(subcategory, subCategoryEntity, entityManager);
        }
    }

    private CategoryEntity createCategoryEntity(Category category, CategoryEntity parentCategory) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryId(category.getId());
        categoryEntity.setCategoryDescription(category.getDescription());
        categoryEntity.setCategoryName(category.getName());
        categoryEntity.setParentCategory(parentCategory);

        return categoryEntity;
    }
}

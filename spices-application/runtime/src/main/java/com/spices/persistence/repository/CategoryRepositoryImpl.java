package com.spices.persistence.repository;

import java.util.List;

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
    public boolean checkIfCategoryExists(Long categoryId, EntityManager entityManager) {
        long results = entityManager.createQuery("SELECT COUNT(c) FROM CategoryEntity c WHERE c.categoryId = :categoryId", Long.class)
                .setParameter("categoryId", categoryId)
                .getSingleResult();
        return results != 0;
    }

    @Override
    public void updateCategory(Category category, EntityManager entityManager) {

        CategoryEntity categoryEntity = getCategory(category.getId(), entityManager);
        categoryEntity.setCategoryDescription(category.getDescription());
        categoryEntity.setCategoryName(category.getName());

    }

    @Override
    public List<CategoryEntity> getCategories(EntityManager entityManager) {
        return entityManager.createQuery("SELECT c FROM CategoryEntity c", CategoryEntity.class)
                .getResultList();
    }

    @Override
    public CategoryEntity getCategory(Long categoryId, EntityManager entityManager) {
        return entityManager.createQuery("SELECT c FROM CategoryEntity c LEFT JOIN FETCH c.parentCategory WHERE c.categoryId = :categoryId", CategoryEntity.class)
                .setParameter("categoryId", categoryId)
                .getSingleResult();
    }

    @Override
    public boolean checkIfCategoryHasSubCategories(Long categoryId, EntityManager entityManager) {
        return entityManager.createQuery("SELECT COUNT(c.categoryId) FROM CategoryEntity c WHERE c.parentCategory.categoryId = :categoryId", Long.class)
                .setParameter("categoryId", categoryId)
                .getSingleResult() != 0;
    }

    @Override
    public void deleteCategories(List<Long> categoryIds, EntityManager entityManager) {
        entityManager.createQuery("DELETE FROM CategoryEntity c WHERE c.categoryId IN (:categoryIds)")
                .setParameter("categoryIds", categoryIds)
                .executeUpdate();
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
        categoryEntity.setCategoryDescription(category.getDescription());
        categoryEntity.setCategoryName(category.getName());
        categoryEntity.setParentCategory(parentCategory);

        return categoryEntity;
    }
}

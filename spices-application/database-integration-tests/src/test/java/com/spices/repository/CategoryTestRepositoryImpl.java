package com.spices.repository;

import java.util.Collections;

import com.spices.domain.Category;
import com.spices.persistence.model.CategoryEntity;
import com.spices.persistence.util.TransactionManager;

public class CategoryTestRepositoryImpl implements CategoryTestRepository {

    private final TransactionManager transactionManager;

    public CategoryTestRepositoryImpl(TransactionManager transactionManager) {

        this.transactionManager = transactionManager;
    }

    @Override
    public Category getCategory(String categoryName) {
        return transactionManager.doInJPAWithoutTransaction(entityManager -> {
            CategoryEntity categoryEntity = entityManager
                    .createQuery("SELECT c FROM CategoryEntity c WHERE c.categoryName = :categoryName", CategoryEntity.class)
                    .setParameter("categoryName", categoryName)
                    .getSingleResult();

            return new Category(
                    categoryEntity.getCategoryId(), null, categoryEntity.getCategoryName(), categoryEntity.getCategoryDescription(), Collections.emptyList()
            );
        });
    }
}

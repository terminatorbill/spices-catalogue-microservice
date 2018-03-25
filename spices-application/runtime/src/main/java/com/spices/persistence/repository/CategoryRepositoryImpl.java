package com.spices.persistence.repository;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.spices.domain.Category;

public class CategoryRepositoryImpl implements CategoryRepository {

    private final Provider<EntityManager> entityManagerProvider;

    @Inject
    CategoryRepositoryImpl(Provider<EntityManager> entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    @Override
    public void createCategory(Category category) {
        EntityManager entityManager = entityManagerProvider.get();


    }
}

package com.spices.persistence.repository;

import java.sql.Connection;

import javax.inject.Inject;

import com.spices.domain.Category;
import com.spices.persistence.util.TransactionManager;

public class CategoryRepositoryFacadeImpl implements CategoryRepositoryFacade {

    private final CategoryRepository categoryRepository;
    private final TransactionManager transactionManager;

    @Inject
    CategoryRepositoryFacadeImpl(CategoryRepository categoryRepository, TransactionManager transactionManager) {
        this.categoryRepository = categoryRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void createCategory(Category category) {
        transactionManager.doInJPA(entityManager -> {
            categoryRepository.createCategory(category, entityManager);
        }, Connection.TRANSACTION_READ_COMMITTED);
    }
}

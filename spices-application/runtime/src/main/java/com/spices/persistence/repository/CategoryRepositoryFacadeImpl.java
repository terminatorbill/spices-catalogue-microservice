package com.spices.persistence.repository;

import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.spices.domain.Category;
import com.spices.persistence.util.TransactionManager;

public class CategoryRepositoryFacadeImpl implements CategoryRepositoryFacade {

    private final CategoryRepository categoryRepository;
    private final TransactionManager transactionManager;

    @Inject
    public CategoryRepositoryFacadeImpl(CategoryRepository categoryRepository, TransactionManager transactionManager) {
        this.categoryRepository = categoryRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void createCategory(Category category) {
        transactionManager.doInJPA(entityManager -> {
            categoryRepository.createCategory(category, entityManager);
        });
    }

    @Override
    public Optional<String> checkAndReturnAnyExistingCategory(Category category) {
        return transactionManager.doInJPAWithoutTransaction(entityManager -> {
            Optional<Category> potentialPresentCategory = category.flattened()
                .filter(c -> checkIfCategoryExists(c, entityManager))
                .findFirst();

            return potentialPresentCategory.map(Category::getName);
        });
    }

    private boolean checkIfCategoryExists(Category category, EntityManager entityManager) {
        return categoryRepository.checkIfCategoryExists(category.getName(), entityManager);
    }
}

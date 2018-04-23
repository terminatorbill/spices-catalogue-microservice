package com.spices.persistence.repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.spices.domain.Category;
import com.spices.persistence.model.CategoryEntity;
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

    @Override
    public boolean checkIfCategoryExists(Category category) {
        return transactionManager.doInJPAWithoutTransaction(entityManager -> checkIfCategoryExists(category, entityManager));
    }

    @Override
    public void updateCategories(List<Category> categories) {
        transactionManager.doInJPA(entityManager -> {
            categories.forEach(category -> categoryRepository.updateCategory(category, entityManager));
        });
    }

    @Override
    public List<Category> getCategories() {
        return transactionManager.doInJPA(entityManager -> {
            return categoryRepository.getCategories(entityManager).stream()
                    .map(this::convertToCategory)
                    .collect(Collectors.toList());
        });
    }

    private Category convertToCategory(CategoryEntity categoryEntity) {
        return new Category(
                categoryEntity.getCategoryId(), categoryEntity.getParentCategory().getCategoryId(), categoryEntity.getCategoryName(), categoryEntity.getCategoryDescription(), Collections.emptyList(), Collections.emptyList()
        );
    }

    private boolean checkIfCategoryExists(Category category, EntityManager entityManager) {
        return categoryRepository.checkIfCategoryExists(category.getName(), entityManager);
    }
}

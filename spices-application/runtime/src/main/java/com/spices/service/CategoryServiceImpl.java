package com.spices.service;

import javax.inject.Inject;

import com.spices.domain.Category;
import com.spices.persistence.repository.CategoryRepositoryFacade;
import com.spices.service.exception.CategoryServiceException;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepositoryFacade categoryRepositoryFacade;

    @Inject
    CategoryServiceImpl(CategoryRepositoryFacade categoryRepositoryFacade) {
        this.categoryRepositoryFacade = categoryRepositoryFacade;
    }

    @Override
    public void createCategory(Category category) {
        checkIfAnyCategoryExists(category);
        categoryRepositoryFacade.createCategory(category);
    }

    private void checkIfAnyCategoryExists(Category category) {
        categoryRepositoryFacade.checkAndReturnAnyExistingCategory(category).ifPresent(categoryId -> {
            throw new CategoryServiceException(categoryId, CategoryServiceException.Type.DUPLICATE_CATEGORY);
        });
    }
}

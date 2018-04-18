package com.spices.service;

import java.util.List;

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
    public void createCategories(Category category) {
        checkIfAnyCategoryExists(category);
        categoryRepositoryFacade.createCategory(category);
    }

    @Override
    public void updateCategories(List<Category> categories) {
        checkIfAnyCategoryDoesNotExist(categories);
        categoryRepositoryFacade.updateCategories(categories);
    }

    private void checkIfAnyCategoryExists(Category category) {
        categoryRepositoryFacade.checkAndReturnAnyExistingCategory(category).ifPresent(categoryName -> {
                throw new CategoryServiceException(categoryName, CategoryServiceException.Type.DUPLICATE_CATEGORY);
        });
    }

    private void checkIfAnyCategoryDoesNotExist(List<Category> categories) {
        categories.stream()
                .filter(categoryRepositoryFacade::checkIfCategoryExists)
                .findAny()
                .ifPresent(category -> {
                    throw new CategoryServiceException(category.getId().toString(), CategoryServiceException.Type.CATEGORY_DOES_NOT_EXIST);
                });
    }
}

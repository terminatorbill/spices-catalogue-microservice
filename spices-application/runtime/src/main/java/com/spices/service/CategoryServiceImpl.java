package com.spices.service;

import javax.inject.Inject;

import com.spices.domain.Category;
import com.spices.persistence.repository.CategoryRepositoryFacade;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepositoryFacade categoryRepositoryFacade;

    @Inject
    CategoryServiceImpl(CategoryRepositoryFacade categoryRepositoryFacade) {
        this.categoryRepositoryFacade = categoryRepositoryFacade;
    }

    @Override
    public void createCategory(Category category) {
        categoryRepositoryFacade.createCategory(category);
    }
}

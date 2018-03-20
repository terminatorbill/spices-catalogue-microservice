package com.spices.service;

import com.spices.persistence.repository.CategoryRepository;
import com.spices.domain.Category;

import javax.inject.Inject;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Inject
    public CategoryServiceImpl(CategoryRepository categoryRepository) {

        this.categoryRepository = categoryRepository;
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.createCategory(category);
    }
}

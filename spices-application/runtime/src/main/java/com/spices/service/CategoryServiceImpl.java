package com.spices.service;

import com.google.inject.persist.Transactional;
import com.spices.persistence.repository.CategoryRepository;
import com.spices.domain.Category;

import javax.inject.Inject;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Inject
    CategoryServiceImpl(CategoryRepository categoryRepository) {

        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void createCategory(Category category) {
        categoryRepository.createCategory(category);
    }
}

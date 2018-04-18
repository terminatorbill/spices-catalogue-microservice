package com.spices.service;

import java.util.List;

import com.spices.domain.Category;

public interface CategoryService {

    void createCategories(Category category);

    void updateCategories(List<Category> categories);
}

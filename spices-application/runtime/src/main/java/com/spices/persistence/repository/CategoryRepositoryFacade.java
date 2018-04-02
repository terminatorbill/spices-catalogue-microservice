package com.spices.persistence.repository;

import java.util.Optional;

import com.spices.domain.Category;

public interface CategoryRepositoryFacade {
    void createCategory(Category category);
    Optional<String> checkAndReturnAnyExistingCategory(Category category);
}

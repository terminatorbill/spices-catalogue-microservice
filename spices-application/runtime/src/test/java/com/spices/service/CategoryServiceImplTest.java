package com.spices.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.spices.domain.Category;
import com.spices.persistence.repository.CategoryRepositoryFacade;

public class CategoryServiceImplTest {
    private final CategoryRepositoryFacade categoryRepositoryFacade = Mockito.mock(CategoryRepositoryFacade.class);
    private final CategoryService categoryService = new CategoryServiceImpl(categoryRepositoryFacade);

    @DisplayName("should create a new category")
    @Test
    public void shouldCreateANewCategory() {
        Category category = new Category(
            null, null, "Foo", "Foo description", Collections.emptyList(), Collections.emptyList()
        );

        categoryService.createCategory(category);

        verify(categoryRepositoryFacade, times(1)).createCategory(category);
    }
}

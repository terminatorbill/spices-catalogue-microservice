package com.spices.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.spices.domain.Category;
import com.spices.persistence.repository.CategoryRepositoryFacade;
import com.spices.service.exception.CategoryServiceException;

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
        when(categoryRepositoryFacade.checkAndReturnAnyExistingCategory(category)).thenReturn(Optional.empty());

        verify(categoryRepositoryFacade, times(1)).createCategory(category);
    }

    @DisplayName("should throw CategoryServiceException with code DUPLICATE_CATEGORY when creating a category that already exists")
    @Test
    public void shouldThrowDuplicateCategoryWhenCreatingCategory() {
        Category category = new Category(
            null, null, "Foo", "Foo description", Collections.emptyList(), Collections.emptyList()
        );

        categoryService.createCategory(category);
        when(categoryRepositoryFacade.checkAndReturnAnyExistingCategory(category)).thenReturn(Optional.of("Foo"));

        CategoryServiceException ex = assertThrows(CategoryServiceException.class, () -> categoryService.createCategory(category));

        assertThat(ex.getType(), is(CategoryServiceException.Type.DUPLICATE_CATEGORY));

        verify(categoryRepositoryFacade, times(1)).createCategory(category);
    }
}

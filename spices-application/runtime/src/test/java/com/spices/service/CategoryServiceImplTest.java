package com.spices.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.spices.api.converter.CategoryToCategoryResponseDtoConverter;
import com.spices.api.dto.CategoryResponseDto;
import com.spices.domain.Category;
import com.spices.persistence.repository.CategoryRepositoryFacade;
import com.spices.service.exception.CategoryServiceException;

public class CategoryServiceImplTest {
    private final CategoryRepositoryFacade categoryRepositoryFacade = Mockito.mock(CategoryRepositoryFacade.class);
    private final CategoryToCategoryResponseDtoConverter toCategoryResponseDtoConverter = new CategoryToCategoryResponseDtoConverter();
    private final CategoryService categoryService = new CategoryServiceImpl(categoryRepositoryFacade, toCategoryResponseDtoConverter);

    @DisplayName("should create a new category")
    @Test
    public void shouldCreateANewCategory() {
        Category category = new Category(
                null, null, "Foo", "Foo description", Collections.emptyList(), Collections.emptyList()
        );

        categoryService.createCategories(category);
        when(categoryRepositoryFacade.checkAndReturnAnyExistingCategory(category)).thenReturn(Optional.empty());

        verify(categoryRepositoryFacade, times(1)).createCategory(category);
    }

    @DisplayName("should update the provided categories")
    @Test
    public void shouldUpdateCategories() {
        Category category1 = new Category(
                1L, null, "Foo", "Foo description", Collections.emptyList(), Collections.emptyList()
        );

        Category category2 = new Category(
                2L, null, "Bar", "Bar description", Collections.emptyList(), Collections.emptyList()
        );

        List<Category> categories = Lists.newArrayList(category1, category2);

        when(categoryRepositoryFacade.checkIfCategoryExists(category1.getId())).thenReturn(true);
        when(categoryRepositoryFacade.checkIfCategoryExists(category2.getId())).thenReturn(true);

        categoryService.updateCategories(categories);

        verify(categoryRepositoryFacade, times(1)).updateCategories(Lists.newArrayList(category1, category2));
    }

    @DisplayName("should retrieve all the categories")
    @Test
    public void shouldRetrieveAllCategories() {
        List<CategoryResponseDto> expectedCategories = Lists.newArrayList(
                new CategoryResponseDto(
                        1L,
                        null,
                        "foo",
                        "foo description"
                ),
                new CategoryResponseDto(
                        2L,
                        1L,
                        "bar",
                        "bar description"
                )
        );

        Category category1 = new Category(
                1L,
                null,
                "foo",
                "foo description",
                Collections.emptyList(),
                Collections.emptyList()
        );

        Category category2 = new Category(
                2L,
                1L,
                "bar",
                "bar description",
                Collections.emptyList(),
                Collections.emptyList()
        );

        when(categoryRepositoryFacade.getCategories()).thenReturn(Lists.newArrayList(category1, category2));

        List<CategoryResponseDto> actualCategories = categoryService.retrieveCategories();

        assertThat(actualCategories.size(), is(expectedCategories.size()));
        assertThat(actualCategories.get(0), is(expectedCategories.get(0)));
        assertThat(actualCategories.get(1), is(expectedCategories.get(1)));
    }

    @DisplayName("should throw CategoryServiceException with code DUPLICATE_CATEGORY when creating a category that already exists")
    @Test
    public void shouldThrowDuplicateCategoryWhenCreatingCategory() {
        Category category = new Category(
                null, null, "Foo", "Foo description", Collections.emptyList(), Collections.emptyList()
        );

        categoryService.createCategories(category);
        when(categoryRepositoryFacade.checkAndReturnAnyExistingCategory(category)).thenReturn(Optional.of("Foo"));

        CategoryServiceException ex = assertThrows(CategoryServiceException.class, () -> categoryService.createCategories(category));

        assertThat(ex.getType(), is(CategoryServiceException.Type.DUPLICATE_CATEGORY));

        verify(categoryRepositoryFacade, times(1)).createCategory(category);
    }

    @DisplayName("should throw CategoryServiceException with code CATEGORY_DOES_NOT_EXIST when updating the provided categories and any category does not exist")
    @Test
    public void shouldThrowCategoryDoesNotExistsWhenUpdatingCategories() {
        Category category1 = new Category(
                1L, null, "Foo", "Foo description", Collections.emptyList(), Collections.emptyList()
        );

        Category category2 = new Category(
                2L, null, "Bar", "Bar description", Collections.emptyList(), Collections.emptyList()
        );

        List<Category> categories = Lists.newArrayList(category1, category2);

        when(categoryRepositoryFacade.checkIfCategoryExists(category1.getId())).thenReturn(false);
        when(categoryRepositoryFacade.checkIfCategoryExists(category2.getId())).thenReturn(true);

        CategoryServiceException ex = assertThrows(CategoryServiceException.class, () -> categoryService.updateCategories(categories));

        assertThat(ex.getType(), is(CategoryServiceException.Type.CATEGORY_DOES_NOT_EXIST));

        verify(categoryRepositoryFacade, times(0)).updateCategories(Lists.newArrayList(category1, category2));
    }
}

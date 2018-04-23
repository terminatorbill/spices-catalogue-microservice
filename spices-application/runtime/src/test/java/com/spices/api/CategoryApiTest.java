package com.spices.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.Response;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.spices.api.converter.CategoryCreationRequestToCategoryConverter;
import com.spices.api.dto.CategoryCreationRequestDto;
import com.spices.api.dto.CategoryResponseDto;
import com.spices.api.dto.CategoryUpdateRequestDto;
import com.spices.api.exception.CategoryAlreadyExistsException;
import com.spices.api.exception.CategoryDoesNotExistsException;
import com.spices.domain.Category;
import com.spices.service.CategoryService;
import com.spices.service.exception.CategoryServiceException;

public class CategoryApiTest {
    private final CategoryService categoryService = Mockito.mock(CategoryService.class);
    private final CategoryCreationRequestToCategoryConverter toCategoryConverter = new CategoryCreationRequestToCategoryConverter();
    private final CategoryApi categoryApi = new CategoryApi(categoryService, toCategoryConverter);

    @DisplayName("should create a new category along with any subcategories and return a 201 Response")
    @Test
    public void shouldCreateCategories() {
        CategoryCreationRequestDto categoryCreationRequestDto = new CategoryCreationRequestDto(
            "Parent Category", "Sample description", Lists.newArrayList(
            new CategoryCreationRequestDto(
                "Child Category", "Sample child description", null
            )
        ));

        ArgumentCaptor<Category> argumentCaptor = ArgumentCaptor.forClass(Category.class);

        Response response = categoryApi.createCategories(categoryCreationRequestDto);
        assertThat(response.getStatus(), is(Response.Status.CREATED.getStatusCode()));

        verify(categoryService, times(1)).createCategories(argumentCaptor.capture());
        Category category = argumentCaptor.getValue();
        assertThat(category.getId(), is(nullValue()));
        assertThat(category.getParentCategoryId(), is(nullValue()));
        assertThat(category.getName(), is(categoryCreationRequestDto.getName()));
        assertThat(category.getDescription(), is(categoryCreationRequestDto.getDescription()));
        assertThat(category.getProducts(), is(empty()));
        assertThat(category.getSubCategories(), hasSize(1));
        assertThat(category.getSubCategories().get(0).getId(), is(nullValue()));
        assertThat(category.getSubCategories().get(0).getParentCategoryId(), is(nullValue()));
        assertThat(category.getSubCategories().get(0).getName(), is(categoryCreationRequestDto.getSubCategories().get(0).getName()));
        assertThat(category.getSubCategories().get(0).getDescription(), is(categoryCreationRequestDto.getSubCategories().get(0).getDescription()));
        assertThat(category.getSubCategories().get(0).getProducts(), is(empty()));
        assertThat(category.getSubCategories().get(0).getSubCategories(), is(empty()));

    }

    @DisplayName("should update all provided categories and return a 201 Response")
    @Test
    public void shouldUpdateCategories() {
        List<CategoryUpdateRequestDto> updateRequestDtos = Lists.newArrayList(
            new CategoryUpdateRequestDto(1L, "foo", "foo description"),
            new CategoryUpdateRequestDto(2L, "bar", "bar description")
        );

        ArgumentCaptor<List<Category>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        Response response = categoryApi.updateCategories(updateRequestDtos);
        assertThat(response.getStatus(), is(Response.Status.CREATED.getStatusCode()));

        verify(categoryService, times(1)).updateCategories(argumentCaptor.capture());
        List<Category> categories = argumentCaptor.getValue();

        assertThat(categories.size(), is(2));
        assertThat(categories.get(0).getId(), is(updateRequestDtos.get(0).getId()));
        assertThat(categories.get(0).getName(), is(updateRequestDtos.get(0).getName()));
        assertThat(categories.get(0).getDescription(), is(updateRequestDtos.get(0).getDescription()));
        assertThat(categories.get(0).getParentCategoryId(), is(nullValue()));
        assertThat(categories.get(0).getProducts(), is(empty()));
        assertThat(categories.get(0).getSubCategories(), is(empty()));

        assertThat(categories.get(1).getId(), is(updateRequestDtos.get(1).getId()));
        assertThat(categories.get(1).getName(), is(updateRequestDtos.get(1).getName()));
        assertThat(categories.get(1).getDescription(), is(updateRequestDtos.get(1).getDescription()));
        assertThat(categories.get(1).getParentCategoryId(), is(nullValue()));
        assertThat(categories.get(1).getProducts(), is(empty()));
        assertThat(categories.get(1).getSubCategories(), is(empty()));
    }

    @DisplayName("should retrieve all categories and return a 200 Response")
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

        when(categoryService.retrieveCategories()).thenReturn(expectedCategories);

        List<CategoryResponseDto> actualCategories = categoryApi.retrieveCategories();

        assertThat(actualCategories, is(expectedCategories));

    }

    @DisplayName("should throw CategoryAlreadyExistsException when a CategoryServiceException with code DUPLICATE_CATEGORY is thrown when creating a new category")
    @Test
    public void shouldThrowCategoryExistsException() {
        CategoryCreationRequestDto categoryCreationRequestDto = new CategoryCreationRequestDto(
            "Parent Category", "Sample description", Lists.newArrayList(
            new CategoryCreationRequestDto(
                "Child Category", "Sample child description", null
            )
        ));

        doThrow(new CategoryServiceException("foo", CategoryServiceException.Type.DUPLICATE_CATEGORY)).when(categoryService).createCategories(any(Category.class));

        ArgumentCaptor<Category> argumentCaptor = ArgumentCaptor.forClass(Category.class);

        assertThrows(CategoryAlreadyExistsException.class, () -> categoryApi.createCategories(categoryCreationRequestDto));

        verify(categoryService, times(1)).createCategories(argumentCaptor.capture());

        Category category = argumentCaptor.getValue();
        assertThat(category.getId(), is(nullValue()));
        assertThat(category.getParentCategoryId(), is(nullValue()));
        assertThat(category.getName(), is(categoryCreationRequestDto.getName()));
        assertThat(category.getDescription(), is(categoryCreationRequestDto.getDescription()));
        assertThat(category.getProducts(), is(empty()));
        assertThat(category.getSubCategories(), hasSize(1));
        assertThat(category.getSubCategories().get(0).getId(), is(nullValue()));
        assertThat(category.getSubCategories().get(0).getParentCategoryId(), is(nullValue()));
        assertThat(category.getSubCategories().get(0).getName(), is(categoryCreationRequestDto.getSubCategories().get(0).getName()));
        assertThat(category.getSubCategories().get(0).getDescription(), is(categoryCreationRequestDto.getSubCategories().get(0).getDescription()));
        assertThat(category.getSubCategories().get(0).getProducts(), is(empty()));
        assertThat(category.getSubCategories().get(0).getSubCategories(), is(empty()));
    }

    @DisplayName("should throw CategoryDoesNotExistsException when a CategoryServiceException with code CATEGORY_DOES_NOT_EXISTS is thrown when updating categories and any category does not exist")
    @Test
    public void shouldThrowCategoryDoesNotExistWhenUpdatingCategories() {
        List<CategoryUpdateRequestDto> updateRequestDtos = Lists.newArrayList(
                new CategoryUpdateRequestDto(1L, "foo", "foo description"),
                new CategoryUpdateRequestDto(2L, "bar", "bar description")
        );

        doThrow(new CategoryServiceException("foo", CategoryServiceException.Type.CATEGORY_DOES_NOT_EXIST)).when(categoryService).updateCategories(anyList());

        ArgumentCaptor<List<Category>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        assertThrows(CategoryDoesNotExistsException.class, () -> categoryApi.updateCategories(updateRequestDtos));

        verify(categoryService, times(1)).updateCategories(argumentCaptor.capture());

        List<Category> categories = argumentCaptor.getValue();

        assertThat(categories.size(), is(2));
        assertThat(categories.get(0).getId(), is(updateRequestDtos.get(0).getId()));
        assertThat(categories.get(0).getName(), is(updateRequestDtos.get(0).getName()));
        assertThat(categories.get(0).getDescription(), is(updateRequestDtos.get(0).getDescription()));
        assertThat(categories.get(0).getParentCategoryId(), is(nullValue()));
        assertThat(categories.get(0).getProducts(), is(empty()));
        assertThat(categories.get(0).getSubCategories(), is(empty()));

        assertThat(categories.get(1).getId(), is(updateRequestDtos.get(1).getId()));
        assertThat(categories.get(1).getName(), is(updateRequestDtos.get(1).getName()));
        assertThat(categories.get(1).getDescription(), is(updateRequestDtos.get(1).getDescription()));
        assertThat(categories.get(1).getParentCategoryId(), is(nullValue()));
        assertThat(categories.get(1).getProducts(), is(empty()));
        assertThat(categories.get(1).getSubCategories(), is(empty()));
    }
}

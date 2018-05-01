package com.spices.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.ws.rs.core.Response;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.spices.api.converter.CategoryCreationRequestToCategoryConverter;
import com.spices.api.converter.CategoryUpdateRequestToCategoryConverter;
import com.spices.api.dto.CategoryCreationRequestDto;
import com.spices.api.dto.CategoryRequestDto;
import com.spices.api.dto.CategoryResponseDto;
import com.spices.api.dto.CategoryUpdateRequestDto;
import com.spices.api.exception.CannotDeleteParentCategoryException;
import com.spices.api.exception.CategoryAlreadyExistsException;
import com.spices.api.exception.CategoryDoesNotExistsException;
import com.spices.domain.Category;
import com.spices.service.CategoryService;
import com.spices.service.exception.CategoryServiceException;

public class CategoryApiTest {
    private final CategoryService categoryService = Mockito.mock(CategoryService.class);
    private final CategoryCreationRequestToCategoryConverter creationRequestToCategoryConverter = new CategoryCreationRequestToCategoryConverter();
    private final CategoryUpdateRequestToCategoryConverter updateRequestToCategoryConverter = new CategoryUpdateRequestToCategoryConverter();
    private final CategoryApi categoryApi = new CategoryApi(categoryService, creationRequestToCategoryConverter, updateRequestToCategoryConverter);

    @DisplayName("should create a new category without a parent category and return a 201 Response")
    @Test
    public void shouldCreateCategories() {
        CategoryCreationRequestDto categoryCreationRequestDto = new CategoryCreationRequestDto();
        categoryCreationRequestDto.setCategories(Lists.newArrayList(new CategoryRequestDto("Category", "Description", null)));

        ArgumentCaptor<List<Category>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        Response response = categoryApi.createCategories(categoryCreationRequestDto);
        assertThat(response.getStatus(), is(Response.Status.CREATED.getStatusCode()));

        verify(categoryService, times(1)).createCategories(argumentCaptor.capture());
        List<Category> categories = argumentCaptor.getValue();
        assertThat(categories.size(), is(1));
        assertThat(categories.get(0).getId(), is(nullValue()));
        assertThat(categories.get(0).getParentCategoryId(), is(nullValue()));
        assertThat(categories.get(0).getName(), is(categoryCreationRequestDto.getCategories().get(0).getName()));
        assertThat(categories.get(0).getDescription(), is(categoryCreationRequestDto.getCategories().get(0).getDescription()));
        assertThat(categories.get(0).getProducts(), is(empty()));
    }

    @DisplayName("should create a new category without a parent category and a new subcategory of an existing category and return a 201 Response")
    @Test
    public void shouldCreateCategoriesWithSubCategories() {
        CategoryCreationRequestDto categoryCreationRequestDto = new CategoryCreationRequestDto();
        categoryCreationRequestDto.setCategories(
                Lists.newArrayList(
                        new CategoryRequestDto("Category", "Description", null),
                        new CategoryRequestDto("Category2", "Description", 1L)
                )
        );

        ArgumentCaptor<List<Category>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        Response response = categoryApi.createCategories(categoryCreationRequestDto);
        assertThat(response.getStatus(), is(Response.Status.CREATED.getStatusCode()));

        verify(categoryService, times(1)).createCategories(argumentCaptor.capture());
        List<Category> categories = argumentCaptor.getValue();
        assertThat(categories.size(), is(2));
        assertThat(categories.get(0).getId(), is(nullValue()));
        assertThat(categories.get(0).getParentCategoryId(), is(nullValue()));
        assertThat(categories.get(0).getName(), is(categoryCreationRequestDto.getCategories().get(0).getName()));
        assertThat(categories.get(0).getDescription(), is(categoryCreationRequestDto.getCategories().get(0).getDescription()));
        assertThat(categories.get(0).getProducts(), is(empty()));

        assertThat(categories.get(1).getId(), is(nullValue()));
        assertThat(categories.get(1).getParentCategoryId(), is(1L));
        assertThat(categories.get(1).getName(), is(categoryCreationRequestDto.getCategories().get(1).getName()));
        assertThat(categories.get(1).getDescription(), is(categoryCreationRequestDto.getCategories().get(1).getDescription()));
        assertThat(categories.get(1).getProducts(), is(empty()));
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

        assertThat(categories.get(1).getId(), is(updateRequestDtos.get(1).getId()));
        assertThat(categories.get(1).getName(), is(updateRequestDtos.get(1).getName()));
        assertThat(categories.get(1).getDescription(), is(updateRequestDtos.get(1).getDescription()));
        assertThat(categories.get(1).getParentCategoryId(), is(nullValue()));
        assertThat(categories.get(1).getProducts(), is(empty()));
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

    @DisplayName("should delete all the provided categories")
    @Test
    public void shouldDeleteAllTheProvidedCategories() {
        String categoryIds = "1, 2,3";

        ArgumentCaptor<List<Long>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        categoryApi.deleteCategories(categoryIds);

        verify(categoryService, times(1)).deleteCategories(argumentCaptor.capture(), eq(false));

        List<Long> categories = argumentCaptor.getValue();

        assertThat(categories.size(), is(3));
        assertThat(categories.get(0), is(1L));
        assertThat(categories.get(1), is(2L));
        assertThat(categories.get(2), is(3L));
    }

    @DisplayName("should throw CategoryAlreadyExistsException when a CategoryServiceException with code DUPLICATE_CATEGORY is thrown when creating a new category")
    @Test
    public void shouldThrowCategoryExistsException() {
        CategoryCreationRequestDto categoryCreationRequestDto = new CategoryCreationRequestDto();
        categoryCreationRequestDto.setCategories(
                Lists.newArrayList(
                        new CategoryRequestDto("Category", "Description", null)
                )
        );

        doThrow(new CategoryServiceException("foo", CategoryServiceException.Type.DUPLICATE_CATEGORY)).when(categoryService).createCategories(anyList());

        ArgumentCaptor<List<Category>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        assertThrows(CategoryAlreadyExistsException.class, () -> categoryApi.createCategories(categoryCreationRequestDto));

        verify(categoryService, times(1)).createCategories(argumentCaptor.capture());

        List<Category> categories = argumentCaptor.getValue();

        assertThat(categories.size(), is(1));

        assertThat(categories.get(0).getId(), is(nullValue()));
        assertThat(categories.get(0).getParentCategoryId(), is(nullValue()));
        assertThat(categories.get(0).getName(), is(categoryCreationRequestDto.getCategories().get(0).getName()));
        assertThat(categories.get(0).getDescription(), is(categoryCreationRequestDto.getCategories().get(0).getDescription()));
        assertThat(categories.get(0).getProducts(), is(empty()));
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

        assertThat(categories.get(1).getId(), is(updateRequestDtos.get(1).getId()));
        assertThat(categories.get(1).getName(), is(updateRequestDtos.get(1).getName()));
        assertThat(categories.get(1).getDescription(), is(updateRequestDtos.get(1).getDescription()));
        assertThat(categories.get(1).getParentCategoryId(), is(nullValue()));
        assertThat(categories.get(1).getProducts(), is(empty()));
    }

    @DisplayName("should throw CannotDeleteParentCategoryException when any of the provided categories to delete is a parent category")
    @Test
    public void shouldThrowCannotDeleteParentCategoryException() {
        String categoryIds = "1, 2,3";

        doThrow(new CategoryServiceException("foo", CategoryServiceException.Type.CANNOT_DELETE_PARENT_CATEGORY)).when(categoryService).deleteCategories(anyList(), eq(false));

        assertThrows(CannotDeleteParentCategoryException.class, () -> categoryApi.deleteCategories(categoryIds));
        verify(categoryService, times(1)).deleteCategories(anyList(), anyBoolean());
    }
}

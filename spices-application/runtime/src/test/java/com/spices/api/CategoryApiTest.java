package com.spices.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.spices.api.converter.CategoryCreationRequestToCategoryConverter;
import com.spices.api.dto.CategoryCreationRequestDto;
import com.spices.domain.Category;
import com.spices.service.CategoryService;

public class CategoryApiTest {
    private final CategoryService categoryService = Mockito.mock(CategoryService.class);
    private final CategoryCreationRequestToCategoryConverter toCategoryConverter = new CategoryCreationRequestToCategoryConverter();
    private final CategoryApi categoryApi = new CategoryApi(categoryService, toCategoryConverter);

    @DisplayName("should create a new category along with any subcategories and return a 201 Response")
    @Test
    public void shouldCreateCategory() {
        CategoryCreationRequestDto categoryCreationRequestDto = new CategoryCreationRequestDto(
            null, "Parent Category", "Sample description", Lists.newArrayList(
            new CategoryCreationRequestDto(
                1L, "Child Category", "Sample child description", null
            )
        ));

        ArgumentCaptor<Category> argumentCaptor = ArgumentCaptor.forClass(Category.class);

        Response response = categoryApi.createCategory(categoryCreationRequestDto);
        assertThat(response.getStatus(), is(Response.Status.CREATED.getStatusCode()));

        verify(categoryService, times(1)).createCategory(argumentCaptor.capture());
        Category category = argumentCaptor.getValue();
        assertThat(category.getId(), is(nullValue()));
        assertThat(category.getParentCategoryId(), is(nullValue()));
        assertThat(category.getName(), is(categoryCreationRequestDto.getName()));
        assertThat(category.getDescription(), is(categoryCreationRequestDto.getDescription()));
        assertThat(category.getProducts(), is(empty()));
        assertThat(category.getSubCategories(), hasSize(1));
        assertThat(category.getSubCategories().get(0).getId(), is(nullValue()));
        assertThat(category.getSubCategories().get(0).getParentCategoryId(), is(1L));
        assertThat(category.getSubCategories().get(0).getName(), is(categoryCreationRequestDto.getSubCategories().get(0).getName()));
        assertThat(category.getSubCategories().get(0).getDescription(), is(categoryCreationRequestDto.getSubCategories().get(0).getDescription()));
        assertThat(category.getSubCategories().get(0).getProducts(), is(empty()));
        assertThat(category.getSubCategories().get(0).getSubCategories(), is(empty()));

    }
}

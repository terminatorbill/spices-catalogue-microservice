package com.spices.api;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.spices.api.converter.CategoryCreationRequestToCategoryConverter;
import com.spices.service.CategoryService;

public class CategoryAdminApiTest {
    private final CategoryService categoryService = Mockito.mock(CategoryService.class);
    private final CategoryCreationRequestToCategoryConverter toCategoryConverter = new CategoryCreationRequestToCategoryConverter();
    private final CategoryAdminApi categoryAdminApi = new CategoryAdminApi(categoryService, toCategoryConverter);

    @DisplayName("should delete all the provided categories")
    @Test
    public void shouldDeleteAllProvidedCategories() {
        String categoryIds = "1, 2,3";

        ArgumentCaptor<List<Long>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        categoryAdminApi.deleteCategories(categoryIds);

        verify(categoryService, times(1)).deleteCategories(argumentCaptor.capture(), eq(true));

        List<Long> categories = argumentCaptor.getValue();

        assertThat(categories.size(), is(3));
        assertThat(categories.get(0), is(1L));
        assertThat(categories.get(1), is(2L));
        assertThat(categories.get(2), is(3L));
    }
}

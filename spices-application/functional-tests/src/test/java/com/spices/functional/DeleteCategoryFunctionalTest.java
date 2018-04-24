package com.spices.functional;

import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.spices.api.dto.CategoryResponseDto;
import com.spices.common.FunctionalTest;

public class DeleteCategoryFunctionalTest extends FunctionalTest {

    @DisplayName("should return 204 (NO_CONTENT) when deleting all the provided categories and none of them has sub-categories")
    @Test
    public void shouldDeleteAllProvidedCategories() {
        createCategory();
        createCategory();

        deleteCategories(getAllCategories().stream().map(CategoryResponseDto::getId).collect(Collectors.toList()));
    }

}

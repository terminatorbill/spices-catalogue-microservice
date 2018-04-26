package com.spices.functional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.javalite.http.Delete;
import org.javalite.http.Http;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.spices.api.dto.CategoryResponseDto;
import com.spices.api.dto.ErrorCodeDto;
import com.spices.api.dto.ErrorDto;
import com.spices.common.FunctionalTest;
import com.spices.common.JsonHelper;
import com.spices.common.TestHelper;

public class DeleteCategoryFunctionalTest extends FunctionalTest {

    @DisplayName("should return 204 (NO_CONTENT) when deleting all the provided categories and none of them has sub-categories")
    @Test
    public void shouldDeleteAllProvidedCategories() {
        createCategory(createRequestDtoWithSingleLevel());
        createCategory(createRequestDtoWithSingleLevel());

        deleteCategories(getAllCategories().stream().map(CategoryResponseDto::getId).collect(Collectors.toList()), false);
    }

    @DisplayName("should return 403 (FORBIDDEN) with code CANNOT_DELETE_PARENT_CATEGORY when any of the provided categories to delete has sub-categories")
    @Test
    public void shouldReturnCannotDeleteParentCategory() {
        createCategory(createRequestDtoWithMultipleSubLevels());
        createCategory(createRequestDtoWithSingleLevel());

        List<Long> categoryIds = getAllCategories().stream().map(CategoryResponseDto::getId).collect(Collectors.toList());

        Delete deleteCategoriesResponse = Http.delete(String.format(TestHelper.CATEGORIES_PATH + "?categoryIds=%s", categoryIds.stream().map(String::valueOf).collect(Collectors.joining(","))));

        assertThat(deleteCategoriesResponse.responseCode(), is(HttpServletResponse.SC_FORBIDDEN));
        ErrorDto errorDto = JsonHelper.toObject(deleteCategoriesResponse.text(), new TypeReference<ErrorDto>() {});
        assertThat(errorDto.getErrorCode(), is(ErrorCodeDto.CANNOT_DELETE_PARENT_CATEGORY));
        assertThat(errorDto.getUuid(), is(nullValue()));
        assertThat(errorDto.getDescription(), is(notNullValue()));
        assertThat(deleteCategoriesResponse.headers().get("Content-Type").get(0), is(MediaType.APPLICATION_JSON));

    }

}

package com.spices.functional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.javalite.http.Http;
import org.javalite.http.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.spices.api.dto.CategoryCreationRequestDto;
import com.spices.api.dto.ErrorCodeDto;
import com.spices.api.dto.ErrorDto;
import com.spices.common.FunctionalTest;
import com.spices.common.JsonHelper;
import com.spices.common.TestHelper;

public class CreateCategoryFunctionalTest extends FunctionalTest {

    @DisplayName("should return 201 (CREATED) when creating one category without subcategories")
    @Test
    public void createRootCategoryOnly() {
        CategoryCreationRequestDto categoryCreationRequestDto = createCategoryCreationRequestDto(2);

        Post createCategoryResponse = Http.post(TestHelper.CATEGORIES_PATH, JsonHelper.toString(categoryCreationRequestDto))
                .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(createCategoryResponse.responseCode(), is(HttpServletResponse.SC_CREATED));
    }

    @DisplayName("should return 409 (CONFLICT) with code CATEGORY_ALREADY_EXISTS when creating two categories with one them being a sub-category of another category and any category exists")
    @Test
    public void throwDuplicateCategory() {
        CategoryCreationRequestDto categoryCreationRequestDto = createCategoryCreationRequestDto(2);

        categoryCreationRequestDto.getCategories().get(0).setParentCategoryId(1L);

        Post createCategoryResponse = Http.post(TestHelper.CATEGORIES_PATH, JsonHelper.toString(categoryCreationRequestDto))
                .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(createCategoryResponse.responseCode(), is(HttpServletResponse.SC_CREATED));

        //We try to re-submit the last query
        createCategoryResponse = Http.post(TestHelper.CATEGORIES_PATH, JsonHelper.toString(categoryCreationRequestDto))
                .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(createCategoryResponse.responseCode(), is(HttpServletResponse.SC_CONFLICT));
        ErrorDto errorDto = JsonHelper.toObject(createCategoryResponse.text(), new TypeReference<ErrorDto>() {});
        assertThat(errorDto.getErrorCode(), is(ErrorCodeDto.CATEGORY_ALREADY_EXISTS));
        assertThat(errorDto.getUuid(), is(nullValue()));
        assertThat(errorDto.getDescription(), is(notNullValue()));
        assertThat(createCategoryResponse.headers().get("Content-Type").get(0), is(MediaType.APPLICATION_JSON));
    }
}

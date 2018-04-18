package com.spices.functional;

import static com.spices.common.TestHelper.generateRandomString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.security.SecureRandom;
import java.util.Collections;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.assertj.core.util.Lists;
import org.javalite.http.Http;
import org.javalite.http.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.spices.api.dto.CategoryCreationRequestDto;
import com.spices.api.dto.ErrorCodeDto;
import com.spices.api.dto.ErrorDto;
import com.spices.common.JsonHelper;
import com.spices.common.TestHelper;

public class CreateCategoryFunctionalTest {

    @DisplayName("should return 201 (CREATED) when creating one category without subcategories")
    @Test
    public void createRootCategoryOnly() {
        CategoryCreationRequestDto categoryCreationRequestDto = createRequestDtoWithSingleLevel();

        Post createCategoryResponse = Http.post(TestHelper.CREATE_CATEGORIES_PATH, JsonHelper.toString(categoryCreationRequestDto))
            .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(createCategoryResponse.responseCode(), is(HttpServletResponse.SC_CREATED));
    }

    @DisplayName("should return 201 (CREATED) when creating one category with one level of subcategories")
    @Test
    public void createCategoryWithOneSubLevel() {
        CategoryCreationRequestDto categoryCreationRequestDto = createRequestDtoWithOneSubLevel();

        Post createCategoryResponse = Http.post(TestHelper.CREATE_CATEGORIES_PATH, JsonHelper.toString(categoryCreationRequestDto))
            .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(createCategoryResponse.responseCode(), is(HttpServletResponse.SC_CREATED));
    }

    @DisplayName("should return 201 (CREATED) when creating one category with multiple levels of subcategories")
    @Test
    public void createCategoryWithMultipleSubLevels() {
        CategoryCreationRequestDto categoryCreationRequestDto = createRequestDtoWithMultipleSubLevels();

        Post createCategoryResponse = Http.post(TestHelper.CREATE_CATEGORIES_PATH, JsonHelper.toString(categoryCreationRequestDto))
            .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(createCategoryResponse.responseCode(), is(HttpServletResponse.SC_CREATED));
    }

    @DisplayName("should return 409 (CONFLICT) with code CATEGORY_ALREADY_EXISTS when creating one category with multiple levels of subcategories and any category exists")
    @Test
    public void throwDuplicateCategory() {
        CategoryCreationRequestDto categoryCreationRequestDto = createRequestDtoWithMultipleSubLevels();

        Post createCategoryResponse = Http.post(TestHelper.CREATE_CATEGORIES_PATH, JsonHelper.toString(categoryCreationRequestDto))
            .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(createCategoryResponse.responseCode(), is(HttpServletResponse.SC_CREATED));

        //we try to resubmit the same query
        createCategoryResponse = Http.post(TestHelper.CREATE_CATEGORIES_PATH, JsonHelper.toString(categoryCreationRequestDto))
            .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(createCategoryResponse.responseCode(), is(HttpServletResponse.SC_CONFLICT));
        ErrorDto errorDto = JsonHelper.toObject(createCategoryResponse.text(), new TypeReference<ErrorDto>() {});
        assertThat(errorDto.getErrorCode(), is(ErrorCodeDto.CATEGORY_ALREADY_EXISTS));
        assertThat(errorDto.getUuid(), is(nullValue()));
        assertThat(errorDto.getDescription(), is(notNullValue()));
        assertThat(createCategoryResponse.headers().get("Content-Type").get(0), is(MediaType.APPLICATION_JSON));

        //we change the root category name to assert that the first sublevel duplicate category will trigger the error
        categoryCreationRequestDto.setName("123");
        createCategoryResponse = Http.post(TestHelper.CREATE_CATEGORIES_PATH, JsonHelper.toString(categoryCreationRequestDto))
            .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(createCategoryResponse.responseCode(), is(HttpServletResponse.SC_CONFLICT));
        errorDto = JsonHelper.toObject(createCategoryResponse.text(), new TypeReference<ErrorDto>() {});
        assertThat(errorDto.getErrorCode(), is(ErrorCodeDto.CATEGORY_ALREADY_EXISTS));
        assertThat(errorDto.getUuid(), is(nullValue()));
        assertThat(errorDto.getDescription(), is(notNullValue()));
        assertThat(createCategoryResponse.headers().get("Content-Type").get(0), is(MediaType.APPLICATION_JSON));

        //we change the root category name and the first level category name to assert that the second sublevel duplicate category will trigger the error
        categoryCreationRequestDto.setName("1234");
        categoryCreationRequestDto.getSubCategories().get(0).setName("123456");
        createCategoryResponse = Http.post(TestHelper.CREATE_CATEGORIES_PATH, JsonHelper.toString(categoryCreationRequestDto))
                .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(createCategoryResponse.responseCode(), is(HttpServletResponse.SC_CONFLICT));
        errorDto = JsonHelper.toObject(createCategoryResponse.text(), new TypeReference<ErrorDto>() {});
        assertThat(errorDto.getErrorCode(), is(ErrorCodeDto.CATEGORY_ALREADY_EXISTS));
        assertThat(errorDto.getUuid(), is(nullValue()));
        assertThat(errorDto.getDescription(), is(notNullValue()));
        assertThat(createCategoryResponse.headers().get("Content-Type").get(0), is(MediaType.APPLICATION_JSON));
    }

    private CategoryCreationRequestDto createRequestDtoWithSingleLevel() {
        return new CategoryCreationRequestDto(
            generateRandomString(6), generateRandomString(6), Collections.emptyList()
        );
    }

    private CategoryCreationRequestDto createRequestDtoWithOneSubLevel() {
        return new CategoryCreationRequestDto(
            generateRandomString(6), generateRandomString(6), Lists.newArrayList(
                new CategoryCreationRequestDto(
                    generateRandomString(6), generateRandomString(6), Collections.emptyList()
                ),
                new CategoryCreationRequestDto(
                    generateRandomString(6), generateRandomString(6), Collections.emptyList()
                )
        )
        );
    }

    private CategoryCreationRequestDto createRequestDtoWithMultipleSubLevels() {
        return new CategoryCreationRequestDto(
            generateRandomString(6), generateRandomString(6), Lists.newArrayList(
            new CategoryCreationRequestDto(generateRandomString(6), generateRandomString(6), Lists.newArrayList(
                new CategoryCreationRequestDto(
                    generateRandomString(6), generateRandomString(6), Lists.newArrayList(
                        new CategoryCreationRequestDto(
                            generateRandomString(6), generateRandomString(6), Collections.emptyList()
                        ))
                ))
            )
        ));
    }
}

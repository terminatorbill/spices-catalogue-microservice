package com.spices.functional;

import static com.spices.common.TestHelper.generateRandomString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.assertj.core.util.Lists;
import org.javalite.http.Get;
import org.javalite.http.Http;
import org.javalite.http.Post;
import org.javalite.http.Put;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.spices.api.dto.CategoryCreationRequestDto;
import com.spices.api.dto.CategoryResponseDto;
import com.spices.api.dto.CategoryUpdateRequestDto;
import com.spices.api.dto.ErrorCodeDto;
import com.spices.api.dto.ErrorDto;
import com.spices.common.JsonHelper;
import com.spices.common.TestHelper;

public class UpdateCategoryFunctionalTest {

    @DisplayName("should return 201 (CREATED) when updating one or more provided categories")
    @Test
    public void shouldUpdateCategories() {
        createCategory();
        createCategory();

        List<CategoryResponseDto> allCategories = getAllCategories();

        List<CategoryUpdateRequestDto> updateRequestDtos = Lists.newArrayList(
                new CategoryUpdateRequestDto(allCategories.get(0).getId(), allCategories.get(0).getName() + "updated", allCategories.get(0).getDescription()),
                new CategoryUpdateRequestDto(allCategories.get(1).getId(), allCategories.get(1).getName() + "updated", allCategories.get(1).getDescription() + "updated")
        );

        updateCategories(updateRequestDtos);
        allCategories = getAllCategories();
        CategoryResponseDto categoryResponseDto = allCategories.stream()
                .filter(c -> c.getId().equals(updateRequestDtos.get(0).getId()))
                .findAny()
                .get();

        assertThat(categoryResponseDto.getDescription(), is(updateRequestDtos.get(0).getDescription()));
        assertThat(categoryResponseDto.getName(), is(updateRequestDtos.get(0).getName()));
        assertThat(categoryResponseDto.getId(), is(updateRequestDtos.get(0).getId()));

        categoryResponseDto = allCategories.stream()
                .filter(c -> c.getId().equals(updateRequestDtos.get(1).getId()))
                .findAny()
                .get();

        assertThat(categoryResponseDto.getDescription(), is(updateRequestDtos.get(1).getDescription()));
        assertThat(categoryResponseDto.getName(), is(updateRequestDtos.get(1).getName()));
        assertThat(categoryResponseDto.getId(), is(updateRequestDtos.get(1).getId()));
    }

    @DisplayName("should return 404 (NOT_FOUND) with code CATEGORY_DOES_NOT_EXIST when updating one or more categories and any of them does not exist")
    @Test
    public void shouldThrowCategoryDoesNotExist() {
        List<CategoryUpdateRequestDto> updateRequestDtos = Lists.newArrayList(
                new CategoryUpdateRequestDto(-5L,"foo", "foo")
        );

        Put updateCategoriesResponse = Http.put(TestHelper.CATEGORIES_PATH, JsonHelper.toString(updateRequestDtos))
                .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(updateCategoriesResponse.responseCode(), is(HttpServletResponse.SC_NOT_FOUND));
        ErrorDto errorDto = JsonHelper.toObject(updateCategoriesResponse.text(), new TypeReference<ErrorDto>() {});
        assertThat(errorDto.getErrorCode(), is(ErrorCodeDto.CATEGORY_DOES_NOT_EXIST));
        assertThat(errorDto.getUuid(), is(nullValue()));
        assertThat(errorDto.getDescription(), is(notNullValue()));
        assertThat(updateCategoriesResponse.headers().get("Content-Type").get(0), is(MediaType.APPLICATION_JSON));
    }

    private void createCategory() {
        CategoryCreationRequestDto categoryCreationRequestDto = createRequestDtoWithSingleLevel();

        Post createCategoryResponse = Http.post(TestHelper.CATEGORIES_PATH, JsonHelper.toString(categoryCreationRequestDto))
                .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(createCategoryResponse.responseCode(), is(HttpServletResponse.SC_CREATED));
    }

    private List<CategoryResponseDto> getAllCategories() {
        Get allCategoriesResponse = Http.get(TestHelper.CATEGORIES_PATH)
                .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(allCategoriesResponse.responseCode(), is(HttpServletResponse.SC_OK));

        List<CategoryResponseDto> categories = JsonHelper.toObject(allCategoriesResponse.text(), new TypeReference<List<CategoryResponseDto>>() {});

        return categories.stream()
                .sorted(Comparator.comparing(CategoryResponseDto::getId))
                .collect(Collectors.toList());
    }

    private void updateCategories(List<CategoryUpdateRequestDto> updateRequestDtos) {
        Put updateCategoriesResponse = Http.put(TestHelper.CATEGORIES_PATH, JsonHelper.toString(updateRequestDtos))
                .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(updateCategoriesResponse.responseCode(), is(HttpServletResponse.SC_CREATED));
    }

    private CategoryCreationRequestDto createRequestDtoWithSingleLevel() {
        return new CategoryCreationRequestDto(
                generateRandomString(6), generateRandomString(6), Collections.emptyList()
        );
    }
}

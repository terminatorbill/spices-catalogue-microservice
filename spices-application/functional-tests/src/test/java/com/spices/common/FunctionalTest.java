package com.spices.common;

import static com.spices.common.TestHelper.generateRandomString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.assertj.core.util.Lists;
import org.javalite.http.Delete;
import org.javalite.http.Get;
import org.javalite.http.Http;
import org.javalite.http.Post;
import org.javalite.http.Put;

import com.fasterxml.jackson.core.type.TypeReference;
import com.spices.api.dto.CategoryCreationRequestDto;
import com.spices.api.dto.CategoryResponseDto;
import com.spices.api.dto.CategoryUpdateRequestDto;

public class FunctionalTest {
    public void createCategory() {
        CategoryCreationRequestDto categoryCreationRequestDto = createRequestDtoWithSingleLevel();

        Post createCategoryResponse = Http.post(TestHelper.CATEGORIES_PATH, JsonHelper.toString(categoryCreationRequestDto))
                .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(createCategoryResponse.responseCode(), is(HttpServletResponse.SC_CREATED));
    }

    public void deleteCategories(List<Long> categoryIds) {
        Delete deleteCategoriesResponse = Http.delete(String.format(TestHelper.CATEGORIES_PATH + "?categoryIds=%s", categoryIds.stream().map(String::valueOf).collect(Collectors.joining(","))));

        assertThat(deleteCategoriesResponse.responseCode(), is(HttpServletResponse.SC_NO_CONTENT));
    }

    public List<CategoryResponseDto> getAllCategories() {
        Get allCategoriesResponse = Http.get(TestHelper.CATEGORIES_PATH)
                .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(allCategoriesResponse.responseCode(), is(HttpServletResponse.SC_OK));

        List<CategoryResponseDto> categories = JsonHelper.toObject(allCategoriesResponse.text(), new TypeReference<List<CategoryResponseDto>>() {});

        return categories.stream()
                .sorted(Comparator.comparing(CategoryResponseDto::getId))
                .collect(Collectors.toList());
    }

    public void updateCategories(List<CategoryUpdateRequestDto> updateRequestDtos) {
        Put updateCategoriesResponse = Http.put(TestHelper.CATEGORIES_PATH, JsonHelper.toString(updateRequestDtos))
                .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(updateCategoriesResponse.responseCode(), is(HttpServletResponse.SC_CREATED));
    }

    public CategoryCreationRequestDto createRequestDtoWithSingleLevel() {
        return new CategoryCreationRequestDto(
                generateRandomString(6), generateRandomString(6), Collections.emptyList()
        );
    }

    public CategoryCreationRequestDto createRequestDtoWithOneSubLevel() {
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

    public CategoryCreationRequestDto createRequestDtoWithMultipleSubLevels() {
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

package com.spices.common;

import static com.spices.common.TestHelper.generateRandomString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.javalite.http.Delete;
import org.javalite.http.Get;
import org.javalite.http.Http;
import org.javalite.http.Post;
import org.javalite.http.Put;
import org.junit.jupiter.api.BeforeAll;

import com.fasterxml.jackson.core.type.TypeReference;
import com.spices.api.dto.CategoryCreationRequestDto;
import com.spices.api.dto.CategoryRequestDto;
import com.spices.api.dto.CategoryResponseDto;
import com.spices.api.dto.CategoryUpdateRequestDto;

public class FunctionalTest {

    @BeforeAll
    public static void setupAll() {
        deleteCategories(getAllCategories().stream().map(CategoryResponseDto::getId).collect(Collectors.toList()), true);
    }

    public static void createCategories(CategoryCreationRequestDto categoryCreationRequestDto) {
        Post createCategoryResponse = Http.post(TestHelper.CATEGORIES_PATH, JsonHelper.toString(categoryCreationRequestDto))
                .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(createCategoryResponse.responseCode(), is(HttpServletResponse.SC_CREATED));
    }

    public static void deleteCategories(List<Long> categoryIds, boolean deleteParentCategories) {
        if (categoryIds.isEmpty()) {
            return;
        }
        String urlPath;
        if (!deleteParentCategories) {
            urlPath = TestHelper.CATEGORIES_PATH;
        } else {
            urlPath = TestHelper.ADMIN_CATEGORIES_PATH;
        }

        Delete deleteCategoriesResponse = Http.delete(String.format(urlPath + "?categoryIds=%s", categoryIds.stream().map(String::valueOf).collect(Collectors.joining(","))));

        assertThat(deleteCategoriesResponse.responseCode(), is(HttpServletResponse.SC_NO_CONTENT));
    }

    public static List<CategoryResponseDto> getAllCategories() {
        Get allCategoriesResponse = Http.get(TestHelper.CATEGORIES_PATH)
                .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(allCategoriesResponse.responseCode(), is(HttpServletResponse.SC_OK));

        List<CategoryResponseDto> categories = JsonHelper.toObject(allCategoriesResponse.text(), new TypeReference<List<CategoryResponseDto>>() {});

        return categories.stream()
                .sorted(Comparator.comparing(CategoryResponseDto::getId))
                .collect(Collectors.toList());
    }

    public static void updateCategories(List<CategoryUpdateRequestDto> updateRequestDtos) {
        Put updateCategoriesResponse = Http.put(TestHelper.CATEGORIES_PATH, JsonHelper.toString(updateRequestDtos))
                .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(updateCategoriesResponse.responseCode(), is(HttpServletResponse.SC_CREATED));
    }

    public static CategoryCreationRequestDto createCategoryCreationRequestDto(int totalCategories) {
        List<CategoryRequestDto> categoriesToCreate = IntStream.range(0, totalCategories)
                .boxed()
                .map(c -> new CategoryRequestDto(generateRandomString(5), generateRandomString(10), null))
                .collect(Collectors.toList());

        CategoryCreationRequestDto categoryCreationRequestDto = new CategoryCreationRequestDto();
        categoryCreationRequestDto.setCategories(categoriesToCreate);

        return categoryCreationRequestDto;
    }
}

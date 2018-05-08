package com.spices.common;

import static com.spices.common.TestHelper.generateRandomNumber;
import static com.spices.common.TestHelper.generateRandomString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.assertj.core.util.Lists;
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
import com.spices.api.dto.ImageRequestDto;
import com.spices.api.dto.MediaRequestDto;
import com.spices.api.dto.ProductRequestDto;
import com.spices.api.dto.ProductResponseDto;
import com.spices.api.dto.VideoRequestDto;

public class FunctionalTest {

    @BeforeAll
    public static void setupAll() {
        deleteCategories(getAllCategories().stream().map(CategoryResponseDto::getId).collect(Collectors.toList()), true);
        deleteProducts();
    }

    public static void createCategories(CategoryCreationRequestDto categoryCreationRequestDto) {
        Post createCategoryResponse = Http.post(TestHelper.CATEGORIES_PATH, JsonHelper.toString(categoryCreationRequestDto))
                .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(createCategoryResponse.responseCode(), is(HttpServletResponse.SC_CREATED));
    }

    public static void createProducts(List<ProductRequestDto> products) {
        Post createProductsResponse = Http.post(TestHelper.PRODUCTS_PATH, JsonHelper.toString(products))
                .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(createProductsResponse.responseCode(), is(HttpServletResponse.SC_CREATED));
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

    public static void deleteProducts() {
        Delete deleteProductsResponse = Http.delete(TestHelper.PRODUCTS_PATH);

        assertThat(deleteProductsResponse.responseCode(), is(HttpServletResponse.SC_NO_CONTENT));
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

    public static List<ProductResponseDto> getProducts(int page, int pageSize) {
        Get productsResponse = Http.get(String.format(TestHelper.PRODUCTS_PATH + "?page-number=%d&page-size=%d", page, pageSize))
                .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(productsResponse.responseCode(), is(HttpServletResponse.SC_OK));

        return JsonHelper.toObject(productsResponse.text(), new TypeReference<List<ProductResponseDto>>() {});
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

    public static ProductRequestDto createProductRequestDto(List<Long> categoryIds) {
        return new ProductRequestDto(
                generateRandomString(5),
                generateRandomString(7),
                categoryIds,
                generateRandomNumber(10),
                new MediaRequestDto(
                        Lists.newArrayList(
                                new ImageRequestDto("http://", generateRandomString(5), "png", generateRandomString(7))
                        ),
                        Lists.newArrayList(
                                new VideoRequestDto("http://", generateRandomString(5), "mp4")
                        )
                )
        );
    }
}

package com.spices.functional;

import static com.spices.common.TestHelper.generateRandomString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collections;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.javalite.http.Http;
import org.javalite.http.Post;
import org.junit.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.spices.api.dto.CategoryCreationRequestDto;
import com.spices.common.JsonHelper;
import com.spices.common.TestHelper;

@Ignore
public class UpdateCategoryFunctionalTest {

    @DisplayName("should return 201 (CREATED) when updating one or more provided categories")
    @Test
    public void shouldUpdateCategories() {
        CategoryCreationRequestDto categoryCreationRequestDto = createRequestDtoWithSingleLevel();

        Post createCategoryResponse = Http.post(TestHelper.CREATE_CATEGORIES_PATH, JsonHelper.toString(categoryCreationRequestDto))
                .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(createCategoryResponse.responseCode(), is(HttpServletResponse.SC_CREATED));

        categoryCreationRequestDto = createRequestDtoWithSingleLevel();

        createCategoryResponse = Http.post(TestHelper.CREATE_CATEGORIES_PATH, JsonHelper.toString(categoryCreationRequestDto))
                .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(createCategoryResponse.responseCode(), is(HttpServletResponse.SC_CREATED));
    }

    private CategoryCreationRequestDto createRequestDtoWithSingleLevel() {
        return new CategoryCreationRequestDto(
                generateRandomString(6), generateRandomString(6), Collections.emptyList()
        );
    }
}

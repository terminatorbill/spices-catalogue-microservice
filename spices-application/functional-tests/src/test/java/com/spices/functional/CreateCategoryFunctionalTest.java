package com.spices.functional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.javalite.http.Http;
import org.javalite.http.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.spices.common.TestHelpers;

public class CreateCategoryFunctionalTest {

    @DisplayName("should create one category with subcategories")
    @Test
    public void createRootCategoryOnly() {
        String createCategoryRequest = TestHelpers.getFileContent("valid/requests/create-categories-single-level.json");

        Post createCategoryResponse = Http.post(TestHelpers.CREATE_CATEGORIES_PATH, createCategoryRequest)
            .header("Content-Type", MediaType.APPLICATION_JSON);

        assertThat(createCategoryResponse.responseCode(), is(HttpServletResponse.SC_CREATED));
        assertThat(createCategoryResponse.headers().get("Content-Type").get(0), is(MediaType.APPLICATION_JSON));
    }
}

package com.spices.api;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.spices.api.converter.CategoryCreationRequestToCategoryConverter;
import com.spices.service.CategoryService;

@Path("admin/categories")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoryAdminApi {

    private final CategoryService categoryService;
    private final CategoryCreationRequestToCategoryConverter toCategoryConverter;

    @Inject
    public CategoryAdminApi(CategoryService categoryService, CategoryCreationRequestToCategoryConverter toCategoryConverter) {
        this.categoryService = categoryService;
        this.toCategoryConverter = toCategoryConverter;
    }

    @DELETE
    public void deleteCategories(@QueryParam("categoryIds") String categoryIds) {
        categoryService.deleteCategories(
                Arrays.stream(categoryIds.split(","))
                        .map(String::trim)
                        .map(Long::parseLong)
                        .collect(Collectors.toList())
                , true);
    }
}

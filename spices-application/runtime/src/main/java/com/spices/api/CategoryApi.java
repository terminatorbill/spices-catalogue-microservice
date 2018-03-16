package com.spices.api;

import com.google.inject.Inject;
import com.spices.api.converter.CategoryCreationRequestToCategoryConverter;
import com.spices.api.dto.CategoryCreationRequestDto;
import com.spices.service.CategoryService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("categories")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoryApi {

    private final CategoryService categoryService;
    private final CategoryCreationRequestToCategoryConverter toCategoryConverter;

    @Inject
    CategoryApi(CategoryService categoryService, CategoryCreationRequestToCategoryConverter toCategoryConverter) {
        this.categoryService = categoryService;
        this.toCategoryConverter = toCategoryConverter;
    }

    @POST
    public Response createCategory(CategoryCreationRequestDto categoryCreationRequestDto) {
        categoryService.createCategory(toCategoryConverter.convert(categoryCreationRequestDto));
        return Response.status(Response.Status.CREATED).build();
    }
}

package com.spices.api;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.spices.api.converter.CategoryCreationRequestToCategoryConverter;
import com.spices.api.dto.CategoryCreationRequestDto;
import com.spices.api.exception.CategoryAlreadyExistsException;
import com.spices.service.CategoryService;
import com.spices.service.exception.CategoryServiceException;

@Path("categories")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoryApi {

    private final CategoryService categoryService;
    private final CategoryCreationRequestToCategoryConverter toCategoryConverter;

    @Inject
    public CategoryApi(CategoryService categoryService, CategoryCreationRequestToCategoryConverter toCategoryConverter) {
        this.categoryService = categoryService;
        this.toCategoryConverter = toCategoryConverter;
    }

    @POST
    public Response createCategory(CategoryCreationRequestDto categoryCreationRequestDto) {
        try {
            categoryService.createCategory(toCategoryConverter.convert(categoryCreationRequestDto));
            return Response.status(Response.Status.CREATED).build();
        } catch (CategoryServiceException e) {
            switch (e.getType()) {
                case DUPLICATE_CATEGORY:
                default:
                    throw new CategoryAlreadyExistsException(String.format("Category with id %s already exists", e.getMessage()));
            }
        }
    }
}

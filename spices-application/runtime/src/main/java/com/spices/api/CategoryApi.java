package com.spices.api;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.spices.api.converter.CategoryCreationRequestToCategoryConverter;
import com.spices.api.dto.CategoryCreationRequestDto;
import com.spices.api.dto.CategoryResponseDto;
import com.spices.api.dto.CategoryUpdateRequestDto;
import com.spices.api.exception.CannotDeleteParentCategoryException;
import com.spices.api.exception.CategoryAlreadyExistsException;
import com.spices.api.exception.CategoryDoesNotExistsException;
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
    public Response createCategories(CategoryCreationRequestDto categoryCreationRequestDto) {
        try {
            categoryService.createCategories(toCategoryConverter.convert(categoryCreationRequestDto));
            return Response.status(Response.Status.CREATED).build();
        } catch (CategoryServiceException e) {
            switch (e.getType()) {
                case DUPLICATE_CATEGORY:
                default:
                    throw new CategoryAlreadyExistsException(String.format("Category with id %s already exists", e.getMessage()));
            }
        }
    }

    @PUT
    public Response updateCategories(List<CategoryUpdateRequestDto> categoryUpdateRequestDtos) {
        try {
            categoryService.updateCategories(toCategoryConverter.convert(categoryUpdateRequestDtos));
            return Response.status(Response.Status.CREATED).build();
        } catch (CategoryServiceException e) {
            switch (e.getType()) {
                case CATEGORY_DOES_NOT_EXIST:
                default:
                    throw new CategoryDoesNotExistsException(String.format("Category with id %s does not exist", e.getMessage()));
            }
        }
    }

    @GET
    public List<CategoryResponseDto> retrieveCategories() {
        return categoryService.retrieveCategories();
    }

    @DELETE
    public void deleteCategories(List<Long> categoryIds) {
        try {
            categoryService.deleteCategories(categoryIds);
        } catch (CategoryServiceException e) {
            switch (e.getType()) {
                case CANNOT_DELETE_PARENT_CATEGORY:
                default:
                    throw new CannotDeleteParentCategoryException(String.format("Category with id %s is already a parent category", e.getMessage()));
            }
        }
    }
}

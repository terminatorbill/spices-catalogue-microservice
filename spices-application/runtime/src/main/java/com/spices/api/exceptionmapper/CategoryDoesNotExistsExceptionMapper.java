package com.spices.api.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.spices.api.dto.ErrorCodeDto;
import com.spices.api.dto.ErrorDto;
import com.spices.api.exception.CategoryDoesNotExistsException;

@Provider
public class CategoryDoesNotExistsExceptionMapper implements ExceptionMapper<CategoryDoesNotExistsException> {
    @Override
    public Response toResponse(CategoryDoesNotExistsException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(new ErrorDto(ErrorCodeDto.CATEGORY_DOES_NOT_EXIST, e.getMessage(), null)).build();
    }
}

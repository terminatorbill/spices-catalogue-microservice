package com.spices.api.exceptionmapper;

import com.spices.api.dto.ErrorCodeDto;
import com.spices.api.dto.ErrorDto;
import com.spices.api.exception.CategoryAlreadyExistsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CategoryAlreadyExistsExceptionMapper implements ExceptionMapper<CategoryAlreadyExistsException> {
    public Response toResponse(CategoryAlreadyExistsException e) {
        return Response.status(Response.Status.CONFLICT).entity(new ErrorDto(ErrorCodeDto.CATEGORY_ALREADY_EXISTS, e.getMessage(), null)).build();
    }
}

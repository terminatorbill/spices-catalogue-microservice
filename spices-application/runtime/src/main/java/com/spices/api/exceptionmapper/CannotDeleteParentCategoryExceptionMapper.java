package com.spices.api.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.spices.api.dto.ErrorCodeDto;
import com.spices.api.dto.ErrorDto;
import com.spices.api.exception.CannotDeleteParentCategoryException;

@Provider
public class CannotDeleteParentCategoryExceptionMapper implements ExceptionMapper<CannotDeleteParentCategoryException> {
    @Override
    public Response toResponse(CannotDeleteParentCategoryException e) {
        return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDto(ErrorCodeDto.CANNOT_DELETE_PARENT_CATEGORY, e.getMessage(), null)).build();
    }
}

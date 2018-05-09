package com.spices.api.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.spices.api.dto.ErrorCodeDto;
import com.spices.api.dto.ErrorDto;
import com.spices.api.exception.ProductAlreadyExistsException;

@Provider
public class ProductAlreadyExistsExceptionMapper implements ExceptionMapper<ProductAlreadyExistsException> {
    @Override
    public Response toResponse(ProductAlreadyExistsException e) {
        return Response.status(Response.Status.CONFLICT).entity(new ErrorDto(ErrorCodeDto.PRODUCT_ALREADY_EXISTS, e.getMessage(), null)).build();
    }
}

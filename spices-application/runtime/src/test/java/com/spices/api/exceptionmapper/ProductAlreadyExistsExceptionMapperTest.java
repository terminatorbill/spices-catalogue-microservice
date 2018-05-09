package com.spices.api.exceptionmapper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.spices.api.dto.ErrorCodeDto;
import com.spices.api.dto.ErrorDto;
import com.spices.api.exception.ProductAlreadyExistsException;

public class ProductAlreadyExistsExceptionMapperTest {

    @DisplayName("should return a 409 response with error code PRODUCT_ALREADY_EXISTS")
    @Test
    public void shouldReturnProductAlreadyExists() {
        ProductAlreadyExistsExceptionMapper productAlreadyExistsExceptionMapper = new ProductAlreadyExistsExceptionMapper();
        ProductAlreadyExistsException ex = new ProductAlreadyExistsException("Foo");

        Response response = productAlreadyExistsExceptionMapper.toResponse(ex);

        ErrorDto errorDto = (ErrorDto) response.getEntity();

        assertThat(response, is(notNullValue()));
        assertThat(response.getStatus(), is(Response.Status.CONFLICT.getStatusCode()));
        assertThat(errorDto.getDescription(), is(notNullValue()));
        assertThat(errorDto.getUuid(), is(nullValue()));
        assertThat(errorDto.getErrorCode(), is(ErrorCodeDto.PRODUCT_ALREADY_EXISTS));
    }
}

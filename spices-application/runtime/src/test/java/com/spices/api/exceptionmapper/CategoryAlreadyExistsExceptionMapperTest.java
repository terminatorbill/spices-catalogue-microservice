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
import com.spices.api.exception.CategoryAlreadyExistsException;

public class CategoryAlreadyExistsExceptionMapperTest {

    @DisplayName("should return a 409 response with error code CATEGORY_ALREADY_EXISTS")
    @Test
    public void shouldReturnCategoryAlreadyExists() {
        CategoryAlreadyExistsExceptionMapper categoryAlreadyExistsExceptionMapper = new CategoryAlreadyExistsExceptionMapper();
        CategoryAlreadyExistsException ex = new CategoryAlreadyExistsException("Foo");

        Response response = categoryAlreadyExistsExceptionMapper.toResponse(ex);

        ErrorDto errorDto = (ErrorDto) response.getEntity();

        assertThat(response, is(notNullValue()));
        assertThat(response.getStatus(), is(Response.Status.CONFLICT.getStatusCode()));
        assertThat(errorDto.getDescription(), is(notNullValue()));
        assertThat(errorDto.getUuid(), is(nullValue()));
        assertThat(errorDto.getErrorCode(), is(ErrorCodeDto.CATEGORY_ALREADY_EXISTS));
    }
}

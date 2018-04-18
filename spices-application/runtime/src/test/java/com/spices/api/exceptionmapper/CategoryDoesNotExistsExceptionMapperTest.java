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
import com.spices.api.exception.CategoryDoesNotExistsException;

public class CategoryDoesNotExistsExceptionMapperTest {

    @DisplayName("should return a 404 response with error code CATEGORY_DOES_NOT_EXIST")
    @Test
    public void shouldReturnCategoryDoesNotExist() {
        CategoryDoesNotExistsExceptionMapper categoryDoesNotExistsExceptionMapper = new CategoryDoesNotExistsExceptionMapper();
        CategoryDoesNotExistsException ex = new CategoryDoesNotExistsException("Foo");

        Response response = categoryDoesNotExistsExceptionMapper.toResponse(ex);

        ErrorDto errorDto = (ErrorDto) response.getEntity();

        assertThat(response, is(notNullValue()));
        assertThat(response.getStatus(), is(Response.Status.NOT_FOUND.getStatusCode()));
        assertThat(errorDto.getDescription(), is(notNullValue()));
        assertThat(errorDto.getUuid(), is(nullValue()));
        assertThat(errorDto.getErrorCode(), is(ErrorCodeDto.CATEGORY_DOES_NOT_EXIST));
    }
}

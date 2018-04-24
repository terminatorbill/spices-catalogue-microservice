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
import com.spices.api.exception.CannotDeleteParentCategoryException;

public class CannotDeleteParentCategoryExceptionMapperTest {

    @DisplayName("should return a 403 response with error code CANNOT_DELETE_PARENT_CATEGORY")
    @Test
    public void shouldReturnCannotDeleteParentCategory() {
        CannotDeleteParentCategoryExceptionMapper cannotDeleteParentCategoryExceptionMapper = new CannotDeleteParentCategoryExceptionMapper();
        CannotDeleteParentCategoryException ex = new CannotDeleteParentCategoryException("Foo");

        Response response = cannotDeleteParentCategoryExceptionMapper.toResponse(ex);

        ErrorDto errorDto = (ErrorDto) response.getEntity();

        assertThat(response, is(notNullValue()));
        assertThat(response.getStatus(), is(Response.Status.FORBIDDEN.getStatusCode()));
        assertThat(errorDto.getDescription(), is(notNullValue()));
        assertThat(errorDto.getUuid(), is(nullValue()));
        assertThat(errorDto.getErrorCode(), is(ErrorCodeDto.CANNOT_DELETE_PARENT_CATEGORY));
    }
}

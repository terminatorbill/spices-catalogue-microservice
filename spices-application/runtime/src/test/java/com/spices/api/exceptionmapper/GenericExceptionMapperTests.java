package com.spices.api.exceptionmapper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.spices.api.dto.ErrorCodeDto;
import com.spices.api.dto.ErrorDto;
import com.spices.api.exception.GenericException;

public class GenericExceptionMapperTests {

    @DisplayName("should return a Response 500 with error code GENERIC")
    @Test
    public void shouldReturnGeneric() {
        GenericExceptionMapper genericExceptionMapper = new GenericExceptionMapper();
        GenericException ex = new GenericException("foo");

        Response response = genericExceptionMapper.toResponse(ex);

        ErrorDto errorDto = (ErrorDto) response.getEntity();

        assertThat(response, is(notNullValue()));
        assertThat(response.getStatus(), is(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
        assertThat(errorDto.getDescription(), is(notNullValue()));
        assertThat(errorDto.getUuid(), is(notNullValue()));
        assertThat(errorDto.getErrorCode(), is(ErrorCodeDto.GENERIC));
    }
}

package com.spices.exceptionmapper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.spices.api.dto.ErrorCodeDto;
import com.spices.api.dto.ErrorDto;

public class GenericExceptionMapperTest {

    @DisplayName("should return a Response 500 with error code GENERIC")
    @Test
    public void shouldReturnGeneric() {
        GenericExceptionMapper genericExceptionMapper = new GenericExceptionMapper();
        Exception ex = new Exception("foo");

        Response response = genericExceptionMapper.toResponse(ex);

        ErrorDto errorDto = (ErrorDto) response.getEntity();

        Assert.assertThat(response, is(notNullValue()));
        Assert.assertThat(response.getStatus(), is(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
        Assert.assertThat(errorDto.getDescription(), is(notNullValue()));
        Assert.assertThat(errorDto.getUuid(), is(notNullValue()));
        Assert.assertThat(errorDto.getErrorCode(), is(ErrorCodeDto.GENERIC));
    }
}

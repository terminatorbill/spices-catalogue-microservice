package com.spices.exceptionmapper;

import java.util.UUID;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spices.api.dto.ErrorCodeDto;
import com.spices.api.dto.ErrorDto;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {
    private static final Logger LOG = LoggerFactory.getLogger(GenericExceptionMapper.class);

    public Response toResponse(Exception e) {
        UUID uuid = UUID.randomUUID();
        log(e, uuid);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorDto(ErrorCodeDto.GENERIC, e.getMessage(), uuid)).build();
    }

    private static void log(Exception e, UUID uuid) {
        LOG.warn("Exception with message {} with uuid {}", e.getMessage(), uuid);
        LOG.warn("Exception {}", e);
    }
}

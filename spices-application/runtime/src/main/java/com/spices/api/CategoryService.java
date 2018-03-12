package com.spices.api;

import com.google.inject.Inject;
import com.spices.api.dto.CreateCategoryRequestDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("categories")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoryService {

    @Inject
    CategoryService() {

    }

    @POST
    public Response addCategory(CreateCategoryRequestDto createCategoryRequestDto) {
        return Response.status(Response.Status.CREATED).build();
    }
}

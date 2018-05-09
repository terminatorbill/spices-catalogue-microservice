package com.spices.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.validator.constraints.NotEmpty;

import com.spices.api.converter.ProductRequestDtoToProductConverter;
import com.spices.api.dto.ProductRequestDto;
import com.spices.api.dto.ProductResponseDto;
import com.spices.api.exception.CategoryDoesNotExistsException;
import com.spices.api.exception.ProductAlreadyExistsException;
import com.spices.domain.Product;
import com.spices.service.ProductService;
import com.spices.service.exception.ProductServiceException;

@Path("products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductApi {

    private final ProductService productService;
    private final ProductRequestDtoToProductConverter productRequestDtoToProductConverter;

    @Inject
    public ProductApi(ProductService productService, ProductRequestDtoToProductConverter productRequestDtoToProductConverter) {
        this.productService = productService;
        this.productRequestDtoToProductConverter = productRequestDtoToProductConverter;
    }

    @POST
    public Response createProducts(List<ProductRequestDto> productRequestDtos) {
        try {
            List<Product> products = productRequestDtos.stream()
                    .map(productRequestDtoToProductConverter::convert)
                    .collect(Collectors.toList());

            productService.createProducts(products);
            return Response.status(Response.Status.CREATED).build();
        } catch (ProductServiceException ex) {
            switch (ex.getType()) {
                case CATEGORY_DOES_NOT_EXIST:
                    throw new CategoryDoesNotExistsException(ex.getMessage(), ex);
                case PRODUCT_ALREADY_EXISTS:
                    throw new ProductAlreadyExistsException(ex.getMessage(), ex);
                default:
                    throw new IllegalStateException();
            }
        }
    }

    @GET
    public List<ProductResponseDto> retrieveProducts(@QueryParam("page-number") @NotNull @Min(0) Integer page, @QueryParam("page-size") @NotNull @Min(1) Integer pageSize) {
        return productService.retrieveProducts(page, pageSize);
    }

    @DELETE
    public void deleteProducts() {
        productService.deleteProducts();
    }
}

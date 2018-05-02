package com.spices.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.spices.api.converter.ProductRequestDtoToProductConverter;
import com.spices.api.dto.ImageDto;
import com.spices.api.dto.MediaDto;
import com.spices.api.dto.ProductRequestDto;
import com.spices.api.dto.VideoDto;
import com.spices.api.exception.CategoryDoesNotExistsException;
import com.spices.api.exception.ProductAlreadyExistsException;
import com.spices.domain.Product;
import com.spices.service.ProductService;
import com.spices.service.exception.ProductServiceException;

public class ProductApiTest {
    private final ProductService productService = Mockito.mock(ProductService.class);
    private final ProductRequestDtoToProductConverter toProductConverter = new ProductRequestDtoToProductConverter();
    private final ProductApi productApi = new ProductApi(productService, toProductConverter);

    @DisplayName("should return 201 Response when creating successfully all the provided products")
    @Test
    public void shouldCreateTheProvidedProducts() {
        List<ProductRequestDto> products = createProducts();

        ArgumentCaptor<List<Product>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        Response response = productApi.createProducts(products);

        verify(productService, times(1)).createProducts(argumentCaptor.capture());

        List<Product> productsToBeCreated = argumentCaptor.getValue();

        verifyCreatedProducts(products, productsToBeCreated);

        assertThat(response.getStatus(), is(Response.Status.CREATED.getStatusCode()));
    }

    @DisplayName("should throw CategoryDoesNotExistsException when any category in the provided products does not exist")
    @Test
    public void shouldReturnCategoryDoesNotExist() {
        List<ProductRequestDto> products = createProducts();

        doThrow(new ProductServiceException("foo", ProductServiceException.Type.CATEGORY_DOES_NOT_EXIST)).when(productService).createProducts(anyList());

        assertThrows(CategoryDoesNotExistsException.class, () -> productApi.createProducts(products));
    }

    @DisplayName("should throw ProductAlreadyExistsException when any of the provided products already exist")
    @Test
    public void shouldReturnProductAlreadyExists() {
        List<ProductRequestDto> products = createProducts();

        doThrow(new ProductServiceException("foo", ProductServiceException.Type.PRODUCT_ALREADY_EXISTS)).when(productService).createProducts(anyList());

        assertThrows(ProductAlreadyExistsException.class, () -> productApi.createProducts(products));
    }

    private static void verifyCreatedProducts(List<ProductRequestDto> products, List<Product> productsToBeCreated) {
        assertThat(productsToBeCreated.size(), is(2));
        assertThat(productsToBeCreated.get(0).getId(), is(nullValue()));
        assertThat(productsToBeCreated.get(0).getName(), is(products.get(0).getName()));
        assertThat(productsToBeCreated.get(0).getDescription(), is(products.get(0).getDescription()));
        assertThat(productsToBeCreated.get(0).getPrice(), is(products.get(0).getPrice()));
        assertThat(productsToBeCreated.get(0).getCategories(), is(products.get(0).getCategories()));
        assertThat(productsToBeCreated.get(0).getMedia().getImages(), is(empty()));
        assertThat(productsToBeCreated.get(0).getMedia().getVideos(), is(empty()));

        assertThat(productsToBeCreated.get(1).getId(), is(nullValue()));
        assertThat(productsToBeCreated.get(1).getName(), is(products.get(1).getName()));
        assertThat(productsToBeCreated.get(1).getDescription(), is(products.get(1).getDescription()));
        assertThat(productsToBeCreated.get(1).getPrice(), is(products.get(1).getPrice()));
        assertThat(productsToBeCreated.get(1).getCategories(), is(products.get(1).getCategories()));
        assertThat(productsToBeCreated.get(1).getMedia().getImages().size(), is(1));
        assertThat(productsToBeCreated.get(1).getMedia().getImages().get(0).getId(), is(nullValue()));
        assertThat(productsToBeCreated.get(1).getMedia().getImages().get(0).getName(), is(products.get(1).getMediaDto().getImages().get(0).getName()));
        assertThat(productsToBeCreated.get(1).getMedia().getImages().get(0).getUrl(), is(products.get(1).getMediaDto().getImages().get(0).getUrl()));
        assertThat(productsToBeCreated.get(1).getMedia().getImages().get(0).getFormat(), is(products.get(1).getMediaDto().getImages().get(0).getFormat()));
        assertThat(productsToBeCreated.get(1).getMedia().getImages().get(0).getCaption(), is(products.get(1).getMediaDto().getImages().get(0).getCaption()));
        assertThat(productsToBeCreated.get(1).getMedia().getVideos().size(), is(1));
        assertThat(productsToBeCreated.get(1).getMedia().getVideos().get(0).getId(), is(nullValue()));
        assertThat(productsToBeCreated.get(1).getMedia().getVideos().get(0).getName(), is(products.get(1).getMediaDto().getVideos().get(0).getName()));
        assertThat(productsToBeCreated.get(1).getMedia().getVideos().get(0).getFormat(), is(products.get(1).getMediaDto().getVideos().get(0).getFormat()));
        assertThat(productsToBeCreated.get(1).getMedia().getVideos().get(0).getUrl(), is(products.get(1).getMediaDto().getVideos().get(0).getUrl()));
    }

    private static ArrayList<ProductRequestDto> createProducts() {
        return Lists.newArrayList(
                new ProductRequestDto(
                        "foo",
                        "foo description",
                        Lists.newArrayList(1L),
                        10000L,
                        null
                ),
                new ProductRequestDto(
                        "bar",
                        "bar description",
                        Lists.newArrayList(1L, 2L),
                        500000L,
                        new MediaDto(
                                Lists.newArrayList(
                                        new ImageDto("http://", "foo", "png", null)
                                ),
                                Lists.newArrayList(
                                        new VideoDto("http://", "foo", "mp4")
                                )
                        )
                )
        );
    }
}

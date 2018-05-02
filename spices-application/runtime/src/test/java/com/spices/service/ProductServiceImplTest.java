package com.spices.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.spices.domain.Image;
import com.spices.domain.Media;
import com.spices.domain.Product;
import com.spices.domain.Video;
import com.spices.persistence.repository.CategoryRepositoryFacade;
import com.spices.persistence.repository.ProductRepositoryFacade;
import com.spices.service.exception.ProductServiceException;

public class ProductServiceImplTest {
    private final ProductRepositoryFacade productRepositoryFacade = Mockito.mock(ProductRepositoryFacade.class);
    private final CategoryRepositoryFacade categoryRepositoryFacade = Mockito.mock(CategoryRepositoryFacade.class);
    private final ProductService productService = new ProductServiceImpl(productRepositoryFacade, categoryRepositoryFacade);

    @DisplayName("should create the provided products")
    @Test
    public void shouldCreateTheProvidedProducts() {
        List<Product> products = createProducts();

        productService.createProducts(products);

        verify(productRepositoryFacade, times(1)).createProducts(products);
    }

    @DisplayName("should throw ProductServiceException with code PRODUCT_ALREADY_EXISTS when any of the provided products already exists")
    @Test
    public void shouldThrowProductAlreadyExists() {
        List<Product> products = createProducts();

        when(productRepositoryFacade.doesAnyProductAlreadyExists(products)).thenReturn(Optional.of(products.get(0).getName()));

        ProductServiceException productServiceException = assertThrows(ProductServiceException.class, () -> productService.createProducts(products));

        assertThat(productServiceException.getType(), is(ProductServiceException.Type.PRODUCT_ALREADY_EXISTS));
        assertThat(productServiceException.getMessage(), is(products.get(0).getName()));
    }

    @DisplayName("should throw ProductServiceException with code CATEGORY_DOES_NOT_EXIST when any category does not exist for any of the provided products")
    @Test
    public void shouldThrowCategoryDoesNotExist() {
        List<Product> products = createProducts();

        List<Long> categories = products.stream().flatMap(product -> product.getCategories().stream()).collect(Collectors.toList());
        when(categoryRepositoryFacade.doesAnyCategoryDoesNotExist(categories)).thenReturn(Optional.of(products.get(0).getCategories().get(0)));

        ProductServiceException productServiceException = assertThrows(ProductServiceException.class, () -> productService.createProducts(products));

        assertThat(productServiceException.getType(), is(ProductServiceException.Type.CATEGORY_DOES_NOT_EXIST));
        assertThat(productServiceException.getMessage(), is(products.get(0).getCategories().get(0).toString()));
    }

    private static List<Product> createProducts() {
        return Lists.newArrayList(
                new Product(
                        null,
                        "foo",
                        "foo description",
                        Lists.newArrayList(1L),
                        100000L,
                        new Media(null, null)
                ),
                new Product(
                        null,
                        "bar",
                        "bar description",
                        Lists.newArrayList(1L, 2L),
                        50000L,
                        new Media(
                                Lists.newArrayList(
                                        new Image(
                                                null, "http://", "foo", "png", null
                                        )
                                ),
                                Lists.newArrayList(
                                        new Video(
                                                null, "http://", "foo", "mp4"
                                        )
                                )
                        )
                )
        );
    }
}

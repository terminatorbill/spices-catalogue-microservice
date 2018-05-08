package com.spices.functional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.spices.api.dto.CategoryResponseDto;
import com.spices.api.dto.ProductRequestDto;
import com.spices.api.dto.ProductResponseDto;
import com.spices.common.FunctionalTest;

public class RetrieveProductFunctionalTest extends FunctionalTest {

    @DisplayName("should return 200 (OK) along with at most the requested products given a page number and a page size")
    @Test
    public void shouldReturnTheRequestedProducts() {
        createCategories(createCategoryCreationRequestDto(2));

        List<Long> allCategories = getAllCategories().stream().map(CategoryResponseDto::getId).collect(Collectors.toList());

        List<ProductRequestDto> productsToCreate = Lists.newArrayList(
                createProductRequestDto(allCategories),
                createProductRequestDto(allCategories)
        );

        createProducts(productsToCreate);

        List<ProductResponseDto> products = getProducts(0, 10);

        assertThat(products.size(), is(2));
        assertThat(products.get(0).getId(), is(notNullValue()));
        assertThat(products.get(0).getName(), is(productsToCreate.get(0).getName()));
        assertThat(products.get(0).getDescription(), is(productsToCreate.get(0).getDescription()));
        assertThat(products.get(0).getCategories(), containsInAnyOrder(productsToCreate.get(0).getCategories().toArray(new Long[0])));
        assertThat(products.get(0).getPrice(), is(productsToCreate.get(0).getPrice()));
        assertThat(products.get(0).getMedia().getImages().size(), is(1));
        assertThat(products.get(0).getMedia().getImages().get(0).getId(), is(notNullValue()));
        assertThat(products.get(0).getMedia().getImages().get(0).getName(), is(productsToCreate.get(0).getMediaRequestDto().getImages().get(0).getName()));
        assertThat(products.get(0).getMedia().getImages().get(0).getUrl(), is(productsToCreate.get(0).getMediaRequestDto().getImages().get(0).getUrl()));
        assertThat(products.get(0).getMedia().getImages().get(0).getCaption(), is(productsToCreate.get(0).getMediaRequestDto().getImages().get(0).getCaption()));
        assertThat(products.get(0).getMedia().getImages().get(0).getFormat(), is(productsToCreate.get(0).getMediaRequestDto().getImages().get(0).getFormat()));
        assertThat(products.get(0).getMedia().getVideos().size(), is(1));
        assertThat(products.get(0).getMedia().getVideos().get(0).getId(), is(notNullValue()));
        assertThat(products.get(0).getMedia().getVideos().get(0).getName(), is(productsToCreate.get(0).getMediaRequestDto().getVideos().get(0).getName()));
        assertThat(products.get(0).getMedia().getVideos().get(0).getFormat(), is(productsToCreate.get(0).getMediaRequestDto().getVideos().get(0).getFormat()));
        assertThat(products.get(0).getMedia().getVideos().get(0).getUrl(), is(productsToCreate.get(0).getMediaRequestDto().getVideos().get(0).getUrl()));

        assertThat(products.get(1).getId(), is(notNullValue()));
        assertThat(products.get(1).getName(), is(productsToCreate.get(1).getName()));
        assertThat(products.get(1).getDescription(), is(productsToCreate.get(1).getDescription()));
        assertThat(products.get(1).getCategories(), containsInAnyOrder(productsToCreate.get(1).getCategories().toArray(new Long[0])));
        assertThat(products.get(1).getPrice(), is(productsToCreate.get(1).getPrice()));
        assertThat(products.get(1).getMedia().getImages().size(), is(1));
        assertThat(products.get(1).getMedia().getImages().get(0).getId(), is(notNullValue()));
        assertThat(products.get(1).getMedia().getImages().get(0).getName(), is(productsToCreate.get(1).getMediaRequestDto().getImages().get(0).getName()));
        assertThat(products.get(1).getMedia().getImages().get(0).getUrl(), is(productsToCreate.get(1).getMediaRequestDto().getImages().get(0).getUrl()));
        assertThat(products.get(1).getMedia().getImages().get(0).getCaption(), is(productsToCreate.get(1).getMediaRequestDto().getImages().get(0).getCaption()));
        assertThat(products.get(1).getMedia().getImages().get(0).getFormat(), is(productsToCreate.get(1).getMediaRequestDto().getImages().get(0).getFormat()));
        assertThat(products.get(1).getMedia().getVideos().size(), is(1));
        assertThat(products.get(1).getMedia().getVideos().get(0).getId(), is(notNullValue()));
        assertThat(products.get(1).getMedia().getVideos().get(0).getName(), is(productsToCreate.get(1).getMediaRequestDto().getVideos().get(0).getName()));
        assertThat(products.get(1).getMedia().getVideos().get(0).getFormat(), is(productsToCreate.get(1).getMediaRequestDto().getVideos().get(0).getFormat()));
        assertThat(products.get(1).getMedia().getVideos().get(0).getUrl(), is(productsToCreate.get(1).getMediaRequestDto().getVideos().get(0).getUrl()));
    }
}

package com.spices.functional;

import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.spices.api.dto.CategoryResponseDto;
import com.spices.api.dto.ProductRequestDto;
import com.spices.common.FunctionalTest;

public class CreateProductFunctionalTest extends FunctionalTest {

    @DisplayName("should return 201 (CREATED) when creating products")
    @Test
    public void shouldCreateProducts() {
        createCategories(createCategoryCreationRequestDto(2));

        List<Long> allCategories = getAllCategories().stream().map(CategoryResponseDto::getId).collect(Collectors.toList());

        List<ProductRequestDto> productsToCreate = Lists.newArrayList(
                createProductRequestDto(allCategories),
                createProductRequestDto(allCategories)
        );

        createProducts(productsToCreate);
    }
}

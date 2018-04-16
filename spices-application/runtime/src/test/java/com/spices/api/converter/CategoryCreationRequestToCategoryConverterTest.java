package com.spices.api.converter;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.spices.api.dto.CategoryCreationRequestDto;
import com.spices.domain.Category;

public class CategoryCreationRequestToCategoryConverterTest {

    private final CategoryCreationRequestToCategoryConverter toCategoryConverter = new CategoryCreationRequestToCategoryConverter();

    @DisplayName("should convert a CategoryCreationRequestDto with subcategories set to null to Category")
    @Test
    public void convertToCategoryWithSubcategoriesToNull() {
        CategoryCreationRequestDto categoryCreationRequestDto = new CategoryCreationRequestDto(
            "Parent Category", "Sample description", null
        );

        Category category = toCategoryConverter.convert(categoryCreationRequestDto);

        assertThat(category.getId(), is(nullValue()));
        assertThat(category.getParentCategoryId(), is(nullValue()));
        assertThat(category.getDescription(), is(categoryCreationRequestDto.getDescription()));
        assertThat(category.getName(), is(categoryCreationRequestDto.getName()));
        assertThat(category.getProducts(), is(empty()));
        assertThat(category.getSubCategories(), is(empty()));
    }

    @DisplayName("should convert a CategoryCreationRequestDto with subcategories set to empty to Category")
    @Test
    public void convertToCategoryWithSubcategoriesToEmpty() {
        CategoryCreationRequestDto categoryCreationRequestDto = new CategoryCreationRequestDto(
            "Parent Category", "Sample description", Collections.emptyList()
        );

        Category category = toCategoryConverter.convert(categoryCreationRequestDto);

        assertThat(category.getId(), is(nullValue()));
        assertThat(category.getParentCategoryId(), is(nullValue()));
        assertThat(category.getDescription(), is(categoryCreationRequestDto.getDescription()));
        assertThat(category.getName(), is(categoryCreationRequestDto.getName()));
        assertThat(category.getProducts(), is(empty()));
        assertThat(category.getSubCategories(), is(empty()));
    }

    @DisplayName("should convert a CategoryCreationRequestDto with one subcategory with null further subcategories to Category")
    @Test
    public void convertToCategoryWithNullSub_Subcategories() {
        CategoryCreationRequestDto categoryCreationRequestDto = new CategoryCreationRequestDto(
            "Parent Category", "Sample description", Lists.newArrayList(
                new CategoryCreationRequestDto(
                    "Child Category", "Sample child description", null
                )
            )
        );

        Category category = toCategoryConverter.convert(categoryCreationRequestDto);

        assertThat(category.getId(), is(nullValue()));
        assertThat(category.getParentCategoryId(), is(nullValue()));
        assertThat(category.getDescription(), is(categoryCreationRequestDto.getDescription()));
        assertThat(category.getName(), is(categoryCreationRequestDto.getName()));
        assertThat(category.getProducts(), is(empty()));
        assertThat(category.getSubCategories(), hasSize(1));
        assertThat(category.getSubCategories().get(0).getId(), is(nullValue()));
        assertThat(category.getSubCategories().get(0).getParentCategoryId(), is(nullValue()));
        assertThat(category.getSubCategories().get(0).getName(), is(categoryCreationRequestDto.getSubCategories().get(0).getName()));
        assertThat(category.getSubCategories().get(0).getDescription(), is(categoryCreationRequestDto.getSubCategories().get(0).getDescription()));
        assertThat(category.getSubCategories().get(0).getProducts(), is(empty()));
        assertThat(category.getSubCategories().get(0).getSubCategories(), is(empty()));
    }

    @DisplayName("should convert a CategoryCreationRequestDto with one subcategory with empty further subcategories to Category")
    @Test
    public void convertToCategoryWithEmptySub_SubCategories() {
        CategoryCreationRequestDto categoryCreationRequestDto = new CategoryCreationRequestDto(
            "Parent Category", "Sample description", Lists.newArrayList(
            new CategoryCreationRequestDto(
                "Child Category", "Sample child description", Collections.emptyList()
            )
        )
        );

        Category category = toCategoryConverter.convert(categoryCreationRequestDto);

        assertThat(category.getId(), is(nullValue()));
        assertThat(category.getParentCategoryId(), is(nullValue()));
        assertThat(category.getDescription(), is(categoryCreationRequestDto.getDescription()));
        assertThat(category.getName(), is(categoryCreationRequestDto.getName()));
        assertThat(category.getProducts(), is(empty()));
        assertThat(category.getSubCategories(), hasSize(1));
        assertThat(category.getSubCategories().get(0).getId(), is(nullValue()));
        assertThat(category.getSubCategories().get(0).getParentCategoryId(), is(nullValue()));
        assertThat(category.getSubCategories().get(0).getName(), is(categoryCreationRequestDto.getSubCategories().get(0).getName()));
        assertThat(category.getSubCategories().get(0).getDescription(), is(categoryCreationRequestDto.getSubCategories().get(0).getDescription()));
        assertThat(category.getSubCategories().get(0).getProducts(), is(empty()));
        assertThat(category.getSubCategories().get(0).getSubCategories(), is(empty()));
    }
}

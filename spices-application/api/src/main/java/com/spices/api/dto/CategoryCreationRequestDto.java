package com.spices.api.dto;

import java.util.Collections;
import java.util.List;

public class CategoryCreationRequestDto {
    private List<CategoryRequestDto> categories = Collections.emptyList();

    public List<CategoryRequestDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryRequestDto> categories) {
        this.categories = categories;
    }
}

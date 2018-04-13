package com.spices.api.dto;

import java.util.List;

public class CategoryCreationRequestDto {
    private String name;
    private String description;
    private List<CategoryCreationRequestDto> subCategories;

    public CategoryCreationRequestDto() {
    }

    public CategoryCreationRequestDto(String name, String description, List<CategoryCreationRequestDto> subCategories) {
        this.name = name;
        this.description = description;
        this.subCategories = subCategories;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<CategoryCreationRequestDto> getSubCategories() {
        return subCategories;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSubCategories(List<CategoryCreationRequestDto> subCategories) {
        this.subCategories = subCategories;
    }
}

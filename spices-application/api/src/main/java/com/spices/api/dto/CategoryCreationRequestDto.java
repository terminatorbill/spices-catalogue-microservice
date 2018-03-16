package com.spices.api.dto;

import java.util.List;

public class CategoryCreationRequestDto {
    private Long parentCategoryId;
    private String name;
    private String description;
    private List<ProductDto> products;
    private List<CategoryCreationRequestDto> subCategories;

    public CategoryCreationRequestDto() {
    }

    public CategoryCreationRequestDto(Long parentCategoryId, String name, String description, List<ProductDto> products, List<CategoryCreationRequestDto> subCategories) {
        this.parentCategoryId = parentCategoryId;
        this.name = name;
        this.description = description;
        this.products = products;
        this.subCategories = subCategories;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public List<CategoryCreationRequestDto> getSubCategories() {
        return subCategories;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

    public void setSubCategories(List<CategoryCreationRequestDto> subCategories) {
        this.subCategories = subCategories;
    }
}

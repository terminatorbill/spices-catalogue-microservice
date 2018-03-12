package com.spices.api.dto;

import java.util.List;

public class CreateCategoryRequestDto {
    private Long parentCategoryId;
    private String name;
    private String description;
    private List<ProductDto> products;
    private List<CreateCategoryRequestDto> subCategories;

    public CreateCategoryRequestDto() {
    }

    public CreateCategoryRequestDto(Long parentCategoryId, String name, String description, List<ProductDto> products, List<CreateCategoryRequestDto> subCategories) {
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

    public List<CreateCategoryRequestDto> getSubCategories() {
        return subCategories;
    }
}

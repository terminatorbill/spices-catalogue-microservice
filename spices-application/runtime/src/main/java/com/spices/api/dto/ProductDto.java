package com.spices.api.dto;

import com.spices.domain.Media;

import java.util.List;

public class ProductDto {
    private String name;
    private String description;
    private List<Long> categories;
    private Long price;
    private MediaDto mediaDto;

    public ProductDto() {
    }

    public ProductDto(String name, String description, List<Long> categories, Long price, MediaDto mediaDto) {
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.price = price;
        this.mediaDto = mediaDto;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Long> getCategories() {
        return categories;
    }

    public Long getPrice() {
        return price;
    }

    public MediaDto getMediaDto() {
        return mediaDto;
    }
}

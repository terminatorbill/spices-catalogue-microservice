package com.spices.api.dto;

import java.util.List;

public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private List<Long> categories;
    private Long price;
    private MediaDto mediaDto;

    public ProductResponseDto() {
    }

    public ProductResponseDto(
            Long id,
            String name,
            String description,
            List<Long> categories,
            Long price,
            MediaDto mediaDto) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.price = price;
        this.mediaDto = mediaDto;
    }

    public Long getId() {
        return id;
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

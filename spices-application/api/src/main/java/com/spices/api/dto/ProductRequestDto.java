package com.spices.api.dto;

import java.util.List;

public class ProductRequestDto {
    private String name;
    private String description;
    private List<Long> categories;
    private Long price;
    private MediaRequestDto mediaRequestDto;

    public ProductRequestDto() {
    }

    public ProductRequestDto(String name, String description, List<Long> categories, Long price, MediaRequestDto mediaRequestDto) {
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.price = price;
        this.mediaRequestDto = mediaRequestDto;
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

    public MediaRequestDto getMediaRequestDto() {
        return mediaRequestDto;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setMediaRequestDto(MediaRequestDto mediaRequestDto) {
        this.mediaRequestDto = mediaRequestDto;
    }
}

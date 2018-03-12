package com.spices.domain;

public class Product {
    private final Long id;
    private final String name;
    private final String description;
    private final Long categoryId; //TODO: Might belong to many categories
    private final Long price;
    private final Media media;

    public Product(Long id, String name, String description, Long categoryId, Long price, Media media) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.price = price;
        this.media = media;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getPrice() {
        return price;
    }

    public Media getMedia() {
        return media;
    }
}

package com.spices.domain;

import java.util.List;
import java.util.Objects;

public class Product {
    private final Long id;
    private final String name;
    private final String description;
    private final List<Long> categories;
    private final Long price;
    private final Media media;

    public Product(Long id, String name, String description, List<Long> categories, Long price, Media media) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categories = categories;
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

    public List<Long> getCategories() {
        return categories;
    }

    public Long getPrice() {
        return price;
    }

    public Media getMedia() {
        return media;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}

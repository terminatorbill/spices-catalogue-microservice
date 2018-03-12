package com.spices.domain;

import java.util.List;

public class Category {
    private final Long id;
    private final String name;
    private final String description;
    private final List<Product> products;
    private final List<Category> subCategories;

    public Category(Long id, String name, String description, List<Product> products, List<Category> subCategories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.products = products;
        this.subCategories = subCategories;
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

    public List<Product> getProducts() {
        return products;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }
}

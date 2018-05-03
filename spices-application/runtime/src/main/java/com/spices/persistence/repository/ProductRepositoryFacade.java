package com.spices.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.spices.domain.Product;

public interface ProductRepositoryFacade {
    void createProducts(List<Product> products);

    Optional<String> isThereAnyProductThatAlreadyExists(List<Product> products);
}

package com.spices.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.spices.api.dto.ProductResponseDto;
import com.spices.domain.Product;

public interface ProductRepositoryFacade {
    void createProducts(List<Product> products);

    Optional<String> isThereAnyProductThatAlreadyExists(List<Product> products);

    List<Product> retrieveProducts(Integer pageNumber, Integer pageSize);

    void deleteProducts();
}

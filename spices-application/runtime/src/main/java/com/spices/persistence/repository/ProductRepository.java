package com.spices.persistence.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.spices.domain.Product;

public interface ProductRepository {
    void createProducts(List<Product> products, EntityManager entityManager);

    boolean checkIfProductAlreadyExists(String productName, EntityManager entityManager);
}

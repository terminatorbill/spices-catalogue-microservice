package com.spices.persistence.repository;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import com.spices.domain.Product;
import com.spices.persistence.util.TransactionManager;

public class ProductRepositoryFacadeImpl implements ProductRepositoryFacade {

    private final ProductRepository productRepository;
    private final TransactionManager transactionManager;

    @Inject
    public ProductRepositoryFacadeImpl(ProductRepository productRepository, TransactionManager transactionManager) {
        this.productRepository = productRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void createProducts(List<Product> products) {
        transactionManager.doInJPA(entityManager -> {
            productRepository.createProducts(products, entityManager);
        });
    }

    @Override
    public Optional<String> isThereAnyProductThatAlreadyExists(List<Product> products) {
        return transactionManager.doInJPAWithoutTransaction(entityManager -> {
            Optional<Product> potentialProductThatAlreadyExists = products.stream()
                    .filter(product -> doesProductAlreadyExists(product.getName()))
                    .findFirst();

            return potentialProductThatAlreadyExists.map(Product::getName);
        });
    }

    @Override
    public List<Product> retrieveProducts(Integer pageNumber, Integer pageSize) {
        return null;
    }

    private boolean doesProductAlreadyExists(String productName) {
        return transactionManager.doInJPAWithoutTransaction(entityManager -> productRepository.checkIfProductAlreadyExists(productName, entityManager));
    }
}

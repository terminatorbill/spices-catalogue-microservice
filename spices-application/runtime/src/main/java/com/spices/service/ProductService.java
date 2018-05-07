package com.spices.service;

import java.util.List;

import com.spices.api.dto.ProductResponseDto;
import com.spices.domain.Product;

public interface ProductService {
    void createProducts(List<Product> products);

    List<ProductResponseDto> retrieveProducts(int page, int pageSize);
}

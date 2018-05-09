package com.spices.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.spices.api.dto.ImageResponseDto;
import com.spices.api.dto.MediaResponseDto;
import com.spices.api.dto.ProductResponseDto;
import com.spices.api.dto.VideoResponseDto;
import com.spices.domain.Media;
import com.spices.domain.Product;
import com.spices.persistence.repository.CategoryRepositoryFacade;
import com.spices.persistence.repository.ProductRepositoryFacade;
import com.spices.service.exception.ProductServiceException;

public class ProductServiceImpl implements ProductService {

    private final ProductRepositoryFacade productRepositoryFacade;
    private final CategoryRepositoryFacade categoryRepositoryFacade;

    @Inject
    public ProductServiceImpl(ProductRepositoryFacade productRepositoryFacade, CategoryRepositoryFacade categoryRepositoryFacade) {
        this.productRepositoryFacade = productRepositoryFacade;
        this.categoryRepositoryFacade = categoryRepositoryFacade;
    }

    @Override
    public void createProducts(List<Product> products) {
        checkIfAnyProductAlreadyExists(products);
        checkIfAnyCategoryDoesNotExist(products);
        productRepositoryFacade.createProducts(products);
    }

    @Override
    public List<ProductResponseDto> retrieveProducts(int page, int pageSize) {
        List<Product> products = productRepositoryFacade.retrieveProducts(page, pageSize);
        return products.stream()
                .map(ProductServiceImpl::convertToProductResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProducts() {
        productRepositoryFacade.deleteProducts();
    }

    private void checkIfAnyProductAlreadyExists(List<Product> products) {
        Optional<String> productAlreadyExists = productRepositoryFacade.isThereAnyProductThatAlreadyExists(products);
        if (productAlreadyExists.isPresent()) {
            throw new ProductServiceException(productAlreadyExists.get(), ProductServiceException.Type.PRODUCT_ALREADY_EXISTS);
        }
    }

    private void checkIfAnyCategoryDoesNotExist(List<Product> products) {
        List<Long> categories = products.stream().flatMap(product -> product.getCategories().stream()).collect(Collectors.toList());
        Optional<Long> categoryDoesNotExist = categoryRepositoryFacade.isThereACategoryThatDoesNotExist(categories);
        if (categoryDoesNotExist.isPresent()) {
            throw new ProductServiceException(categoryDoesNotExist.get().toString(), ProductServiceException.Type.CATEGORY_DOES_NOT_EXIST);
        }
    }

    private static ProductResponseDto convertToProductResponseDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategories(),
                product.getPrice(),
                convertToMediaResponseDto(product.getMedia())
        );
    }

    private static MediaResponseDto convertToMediaResponseDto(Media media) {
        return new MediaResponseDto(
                media.getImages().stream().map(image -> new ImageResponseDto(image.getId(), image.getUrl(), image.getName(), image.getFormat(), image.getCaption())).collect(Collectors.toList()),
                media.getVideos().stream().map(video -> new VideoResponseDto(video.getId(), video.getUrl(), video.getName(), video.getFormat())).collect(Collectors.toList())
        );
    }
}

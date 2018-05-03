package com.spices.persistence.repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import com.spices.domain.Image;
import com.spices.domain.Product;
import com.spices.domain.Video;
import com.spices.persistence.model.CategoryEntity;
import com.spices.persistence.model.ImageEntity;
import com.spices.persistence.model.ProductEntity;
import com.spices.persistence.model.VideoEntity;

public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public void createProducts(List<Product> products, EntityManager entityManager) {
        products.forEach(product -> {
            Set<CategoryEntity> categoriesOfProduct = getCategories(product, entityManager);

            ProductEntity productEntity = new ProductEntity(null, categoriesOfProduct, product.getName(), product.getDescription(), product.getPrice());

            entityManager.persist(productEntity);

            product.getMedia().getImages().forEach(image -> persistImage(image, productEntity, entityManager));
            product.getMedia().getVideos().forEach(video -> persistVideo(video, productEntity, entityManager));
        });
    }

    @Override
    public boolean checkIfProductAlreadyExists(String productName, EntityManager entityManager) {
        long results = entityManager.createQuery("SELECT COUNT(p) FROM Product p WHERE p.productName = :productName", Long.class)
                .setParameter("productName", productName)
                .getSingleResult();

        return results != 0;
    }

    private static Set<CategoryEntity> getCategories(Product product, EntityManager entityManager) {
        return product.getCategories().stream()
                .map(categoryId -> entityManager.find(CategoryEntity.class, categoryId))
                .collect(Collectors.toSet());
    }

    private static void persistImage(Image image, ProductEntity productEntity, EntityManager entityManager) {
        ImageEntity imageEntity = new ImageEntity(
                null,
                image.getUrl(),
                image.getName(),
                image.getFormat(),
                image.getCaption(),
                productEntity
        );

        entityManager.persist(imageEntity);
    }

    private static void persistVideo(Video video, ProductEntity productEntity, EntityManager entityManager) {
        VideoEntity videoEntity = new VideoEntity(
                null,
                video.getUrl(),
                video.getName(),
                video.getFormat(),
                productEntity
        );

        entityManager.persist(videoEntity);
    }

}

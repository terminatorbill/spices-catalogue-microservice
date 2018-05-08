package com.spices.persistence.repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import com.spices.domain.Image;
import com.spices.domain.Media;
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

            ProductEntity productEntity = new ProductEntity();
            Set<ImageEntity> images = product.getMedia().getImages().stream()
                    .map(image -> convertToImageEntity(image, productEntity))
                    .collect(Collectors.toSet());

            Set<VideoEntity> video = product.getMedia().getVideos().stream()
                    .map(v -> convertToVideoEntity(v, productEntity))
                    .collect(Collectors.toSet());

            productEntity.setCategories(categoriesOfProduct);
            productEntity.setProductDescription(product.getDescription());
            productEntity.setProductName(product.getName());
            productEntity.setProductPrice(product.getPrice());
            productEntity.addImages(images);
            productEntity.addVideo(video);

            entityManager.persist(productEntity);
        });
    }

    @Override
    public boolean checkIfProductAlreadyExists(String productName, EntityManager entityManager) {
        long results = entityManager.createQuery("SELECT COUNT(p) FROM ProductEntity p WHERE p.productName = :productName", Long.class)
                .setParameter("productName", productName)
                .getSingleResult();

        return results != 0;
    }

    @Override
    public List<Product> retrieveProducts(Integer pageNumber, Integer pageSize, EntityManager entityManager) {
        List<ProductEntity> products = entityManager.createQuery("SELECT p FROM ProductEntity p LEFT JOIN FETCH p.categories", ProductEntity.class)
                .setFirstResult(pageNumber)
                .setMaxResults(pageSize)
                .getResultList();

        return products.stream()
                .map(ProductRepositoryImpl::convertToProduct)
                .collect(Collectors.toList());
    }

    private static Set<CategoryEntity> getCategories(Product product, EntityManager entityManager) {
        return product.getCategories().stream()
                .map(categoryId -> entityManager.find(CategoryEntity.class, categoryId))
                .collect(Collectors.toSet());
    }

    private static Product convertToProduct(ProductEntity productEntity) {
        return new Product(
                productEntity.getProductId(),
                productEntity.getProductName(),
                productEntity.getProductDescription(),
                productEntity.getCategories().stream().map(CategoryEntity::getCategoryId).collect(Collectors.toList()),
                productEntity.getProductPrice(),
                convertToMedia(productEntity.getImages(), productEntity.getVideo())
        );
    }

    private static Media convertToMedia(Set<ImageEntity> images, Set<VideoEntity> video) {
        return new Media(
                images.stream().map(ProductRepositoryImpl::convertToImage).collect(Collectors.toList()),
                video.stream().map(ProductRepositoryImpl::convertToVideo).collect(Collectors.toList())
        );
    }

    private static Image convertToImage(ImageEntity imageEntity) {
        return new Image(
                imageEntity.getImageId(),
                imageEntity.getUrl(),
                imageEntity.getName(),
                imageEntity.getFormat(),
                imageEntity.getCaption()
        );
    }

    private static Video convertToVideo(VideoEntity videoEntity) {
        return new Video(
              videoEntity.getVideoId(),
              videoEntity.getUrl(),
              videoEntity.getName(),
              videoEntity.getFormat()
        );
    }

    private static ImageEntity convertToImageEntity(Image image, ProductEntity productEntity) {
        return new ImageEntity(
                null,
                image.getUrl(),
                image.getName(),
                image.getFormat(),
                image.getCaption(),
                productEntity
        );
    }

    private static VideoEntity convertToVideoEntity(Video video, ProductEntity productEntity) {
        return new VideoEntity(
                null,
                video.getUrl(),
                video.getName(),
                video.getFormat(),
                productEntity
        );
    }
}

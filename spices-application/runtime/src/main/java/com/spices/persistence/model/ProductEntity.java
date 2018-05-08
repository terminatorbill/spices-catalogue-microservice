package com.spices.persistence.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.common.collect.Sets;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @Column(name = "product_id")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
    @GeneratedValue(generator = "product_seq", strategy = GenerationType.SEQUENCE)
    private Long productId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
        name = "category_product",
        joinColumns = @JoinColumn(name = "category_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<CategoryEntity> categories = Sets.newHashSet();

    @Column(name = "product_name", unique = true)
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_price")
    private Long productPrice;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @Column(name = "images")
    private Set<ImageEntity> images = Sets.newHashSet();

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @Column(name = "video")
    private Set<VideoEntity> video = Sets.newHashSet();

    public ProductEntity() {
    }

    public ProductEntity(
            Long productId,
            Set<CategoryEntity> categories,
            String productName,
            String productDescription,
            Long productPrice,
            Set<ImageEntity> images,
            Set<VideoEntity> video) {
        this.productId = productId;
        this.categories = categories;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.images = images;
        this.video = video;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }

    public Set<ImageEntity> getImages() {
        return images;
    }

    public void addImages(Set<ImageEntity> images) {
        images.forEach(this::addImage);
    }

    public void addVideo(Set<VideoEntity> video) {
        video.forEach(this::addVideo);
    }

    public void addImage(ImageEntity image) {
        images.add(image);
        image.setProduct(this);
    }

    public Set<VideoEntity> getVideo() {
        return video;
    }

    public void addVideo(VideoEntity video) {
        this.video.add(video);
        video.setProduct(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductEntity)) return false;
        ProductEntity that = (ProductEntity) o;
        return Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(productName);
    }
}

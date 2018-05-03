package com.spices.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Objects;

@Entity
@Table(name = "image", uniqueConstraints = {
    @UniqueConstraint(name = "image_unc", columnNames = {"image_name", "image_format"})
})
public class ImageEntity {

    @Id
    @Column(name = "image_id")
    @SequenceGenerator(name = "image_seq", sequenceName = "image_seq", allocationSize = 1)
    @GeneratedValue(generator = "image_seq", strategy = GenerationType.SEQUENCE)
    private Long imageId;

    @Column(name = "image_url")
    private String url;

    @Column(name = "image_name")
    private String name;

    @Column(name = "image_format")
    private String format;

    @Column(name = "image_caption")
    private String caption;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    public ImageEntity() {
    }

    public ImageEntity(Long imageId, String url, String name, String format, String caption, ProductEntity product) {
        this.imageId = imageId;
        this.url = url;
        this.name = name;
        this.format = format;
        this.caption = caption;
        this.product = product;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageEntity)) return false;
        ImageEntity that = (ImageEntity) o;
        return Objects.equals(name, that.name) &&
            Objects.equals(format, that.format);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, format);
    }
}

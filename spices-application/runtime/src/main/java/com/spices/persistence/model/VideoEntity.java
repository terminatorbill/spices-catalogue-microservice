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
@Table(name = "video", uniqueConstraints = {
    @UniqueConstraint(name = "video_unc", columnNames = {"video_name", "video_format"})
})
public class VideoEntity {

    @Id
    @Column(name = "video_id")
    @SequenceGenerator(name = "video_seq", sequenceName = "video_seq", allocationSize = 1)
    @GeneratedValue(generator = "video_seq", strategy = GenerationType.SEQUENCE)
    private Long videoId;

    @Column(name = "video_url")
    private String url;

    @Column(name = "video_name")
    private String name;

    @Column(name = "video_format")
    private String format;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    public VideoEntity() {
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
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

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoEntity)) return false;
        VideoEntity that = (VideoEntity) o;
        return Objects.equals(name, that.name) &&
            Objects.equals(format, that.format);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, format);
    }
}

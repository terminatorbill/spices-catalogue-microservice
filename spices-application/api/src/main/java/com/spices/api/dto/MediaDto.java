package com.spices.api.dto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MediaDto {
    private List<ImageDto> images;
    private List<VideoDto> videos;

    public MediaDto() {
    }

    public MediaDto(List<ImageDto> images, List<VideoDto> videos) {
        this.images = Optional.ofNullable(images).orElse(Collections.emptyList());
        this.videos = Optional.ofNullable(videos).orElse(Collections.emptyList());
    }

    public List<ImageDto> getImages() {
        return images;
    }

    public List<VideoDto> getVideos() {
        return videos;
    }

    public void setImages(List<ImageDto> images) {
        this.images = images;
    }

    public void setVideos(List<VideoDto> videos) {
        this.videos = videos;
    }
}

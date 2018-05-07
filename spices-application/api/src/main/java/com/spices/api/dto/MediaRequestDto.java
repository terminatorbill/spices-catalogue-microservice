package com.spices.api.dto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MediaRequestDto {
    private List<ImageRequestDto> images;
    private List<VideoRequestDto> videos;

    public MediaRequestDto() {
    }

    public MediaRequestDto(List<ImageRequestDto> images, List<VideoRequestDto> videos) {
        this.images = Optional.ofNullable(images).orElse(Collections.emptyList());
        this.videos = Optional.ofNullable(videos).orElse(Collections.emptyList());
    }

    public List<ImageRequestDto> getImages() {
        return images;
    }

    public List<VideoRequestDto> getVideos() {
        return videos;
    }

    public void setImages(List<ImageRequestDto> images) {
        this.images = images;
    }

    public void setVideos(List<VideoRequestDto> videos) {
        this.videos = videos;
    }
}

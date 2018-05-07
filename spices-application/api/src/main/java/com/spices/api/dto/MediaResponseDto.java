package com.spices.api.dto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MediaResponseDto {
    private List<ImageResponseDto> images;
    private List<VideoResponseDto> videos;

    public MediaResponseDto() {
    }

    public MediaResponseDto(List<ImageResponseDto> images, List<VideoResponseDto> videos) {
        this.images = Optional.ofNullable(images).orElse(Collections.emptyList());
        this.videos = Optional.ofNullable(videos).orElse(Collections.emptyList());
    }

    public List<ImageResponseDto> getImages() {
        return images;
    }

    public List<VideoResponseDto> getVideos() {
        return videos;
    }
}

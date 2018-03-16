package com.spices.api.dto;

import java.util.List;

public class MediaDto {
    private List<ImageDto> images;
    private List<VideoDto> videos;

    public MediaDto() {
    }

    public MediaDto(List<ImageDto> images, List<VideoDto> videos) {
        this.images = images;
        this.videos = videos;
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

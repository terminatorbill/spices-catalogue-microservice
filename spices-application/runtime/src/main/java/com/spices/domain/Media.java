package com.spices.domain;

import java.util.List;

public class Media {
    private final List<Image> images;
    private final List<Video> videos;

    public Media(List<Image> images, List<Video> videos) {
        this.images = images;
        this.videos = videos;
    }

    public List<Image> getImages() {
        return images;
    }

    public List<Video> getVideos() {
        return videos;
    }
}

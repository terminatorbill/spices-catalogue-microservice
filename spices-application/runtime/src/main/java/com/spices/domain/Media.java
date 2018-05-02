package com.spices.domain;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Media {
    private final List<Image> images;
    private final List<Video> videos;

    public Media(List<Image> images, List<Video> videos) {
        this.images = Optional.ofNullable(images).orElse(Collections.emptyList());
        this.videos = Optional.ofNullable(videos).orElse(Collections.emptyList());
    }

    public List<Image> getImages() {
        return images;
    }

    public List<Video> getVideos() {
        return videos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Media)) return false;
        Media media = (Media) o;
        return Objects.equals(images, media.images) &&
                Objects.equals(videos, media.videos);
    }

    @Override
    public int hashCode() {

        return Objects.hash(images, videos);
    }
}

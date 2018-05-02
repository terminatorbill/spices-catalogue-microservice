package com.spices.domain;

import java.util.Objects;

public class Video {
    private final Long id;
    private final String url;
    private final String name;
    private final String format;

    public Video(Long id, String url, String name, String format) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.format = format;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getFormat() {
        return format;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Video)) return false;
        Video video = (Video) o;
        return Objects.equals(name, video.name) &&
                Objects.equals(format, video.format);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, format);
    }
}

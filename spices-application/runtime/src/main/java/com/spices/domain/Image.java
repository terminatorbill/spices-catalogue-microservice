package com.spices.domain;

import java.util.Objects;

public class Image {
    private final Long id;
    private final String url;
    private final String name;
    private final String format;
    private final String caption;

    public Image(Long id, String url, String name, String format, String caption) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.format = format;
        this.caption = caption;
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

    public String getCaption() {
        return caption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image)) return false;
        Image image = (Image) o;
        return Objects.equals(name, image.name) &&
                Objects.equals(format, image.format);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, format);
    }
}

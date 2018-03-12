package com.spices.domain;

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
}

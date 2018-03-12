package com.spices.domain;

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
}

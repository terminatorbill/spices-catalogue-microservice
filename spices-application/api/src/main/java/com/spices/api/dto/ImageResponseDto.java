package com.spices.api.dto;

public class ImageResponseDto {
    private Long id;
    private String url;
    private String name;
    private String format;
    private String caption;

    public ImageResponseDto() {
    }

    public ImageResponseDto(Long id, String url, String name, String format, String caption) {
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

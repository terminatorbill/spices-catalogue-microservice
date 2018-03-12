package com.spices.api.dto;

public class ImageDto {
    private String url;
    private String name;
    private String format;
    private String caption;

    public ImageDto() {
    }

    public ImageDto(String url, String name, String format, String caption) {
        this.url = url;
        this.name = name;
        this.format = format;
        this.caption = caption;
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

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}

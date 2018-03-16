package com.spices.api.dto;

public class VideoDto {
    private String url;
    private String name;
    private String format;

    public VideoDto() {
    }

    public VideoDto(String url, String name, String format) {
        this.url = url;
        this.name = name;
        this.format = format;
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

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}

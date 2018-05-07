package com.spices.api.dto;

public class VideoResponseDto {
    private Long id;
    private String url;
    private String name;
    private String format;

    public VideoResponseDto() {
    }

    public VideoResponseDto(Long id, String url, String name, String format) {
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

package com.spices.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.spices.api.dto.ImageRequestDto;
import com.spices.api.dto.MediaRequestDto;
import com.spices.api.dto.ProductRequestDto;
import com.spices.api.dto.VideoRequestDto;
import com.spices.domain.Image;
import com.spices.domain.Media;
import com.spices.domain.Product;
import com.spices.domain.Video;

public class ProductRequestDtoToProductConverter {

    public Product convert(ProductRequestDto productRequestDto) {
        return new Product(
                null,
                productRequestDto.getName(),
                productRequestDto.getDescription(),
                productRequestDto.getCategories(),
                productRequestDto.getPrice(),
                convertMedia(productRequestDto.getMediaRequestDto())
        );
    }

    private Media convertMedia(MediaRequestDto mediaRequestDto) {
        if (mediaRequestDto == null) {
            return new Media(null, null);
        }

        return new Media(
                getImages(mediaRequestDto.getImages()),
                getVideo(mediaRequestDto.getVideos())
        );
    }

    private List<Image> getImages(List<ImageRequestDto> images) {
        return images.stream()
                .map(this::getImage)
                .collect(Collectors.toList());
    }

    private Image getImage(ImageRequestDto imageRequestDto) {
        return new Image(
                null,
                imageRequestDto.getUrl(),
                imageRequestDto.getName(),
                imageRequestDto.getFormat(),
                imageRequestDto.getCaption()
        );
    }

    private List<Video> getVideo(List<VideoRequestDto> videos) {
        return videos.stream()
                .map(this::getVideo)
                .collect(Collectors.toList());
    }

    private Video getVideo(VideoRequestDto videoRequestDto) {
        return new Video(
                null,
                videoRequestDto.getUrl(),
                videoRequestDto.getName(),
                videoRequestDto.getFormat()
        );
    }
}

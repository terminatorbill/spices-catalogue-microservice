package com.spices.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.spices.api.dto.ImageDto;
import com.spices.api.dto.MediaDto;
import com.spices.api.dto.ProductRequestDto;
import com.spices.api.dto.VideoDto;
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
                convertMedia(productRequestDto.getMediaDto())
        );
    }

    private Media convertMedia(MediaDto mediaDto) {
        if (mediaDto == null) {
            return new Media(null, null);
        }

        return new Media(
                getImages(mediaDto.getImages()),
                getVideo(mediaDto.getVideos())
        );
    }

    private List<Image> getImages(List<ImageDto> images) {
        return images.stream()
                .map(this::getImage)
                .collect(Collectors.toList());
    }

    private Image getImage(ImageDto imageDto) {
        return new Image(
                null,
                imageDto.getUrl(),
                imageDto.getName(),
                imageDto.getFormat(),
                imageDto.getCaption()
        );
    }

    private List<Video> getVideo(List<VideoDto> videos) {
        return videos.stream()
                .map(this::getVideo)
                .collect(Collectors.toList());
    }

    private Video getVideo(VideoDto videoDto) {
        return new Video(
                null,
                videoDto.getUrl(),
                videoDto.getName(),
                videoDto.getFormat()
        );
    }
}

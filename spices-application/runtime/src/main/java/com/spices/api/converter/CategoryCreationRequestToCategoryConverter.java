package com.spices.api.converter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.spices.api.dto.CategoryCreationRequestDto;
import com.spices.api.dto.ImageDto;
import com.spices.api.dto.MediaDto;
import com.spices.api.dto.ProductDto;
import com.spices.api.dto.VideoDto;
import com.spices.domain.Category;
import com.spices.domain.Image;
import com.spices.domain.Media;
import com.spices.domain.Product;
import com.spices.domain.Video;

public class CategoryCreationRequestToCategoryConverter {

    public Category convert(CategoryCreationRequestDto categoryCreationRequestDto) {
        return new Category(
            null,
            categoryCreationRequestDto.getParentCategoryId(),
            categoryCreationRequestDto.getName(),
            categoryCreationRequestDto.getDescription(),
                Collections.emptyList(),
            getSubCategories(categoryCreationRequestDto.getSubCategories())
        );
    }

    private List<Category> getSubCategories(List<CategoryCreationRequestDto> subCategories) {
        return subCategories.stream()
            .map(this::convert)
            .collect(Collectors.toList());
    }

    private static Product getProduct(ProductDto productDto) {
        return new Product(
            null,
            productDto.getName(),
            productDto.getDescription(),
            productDto.getCategories(),
            productDto.getPrice(),
            getMedia(productDto.getMediaDto())
        );
    }

    private static Media getMedia(MediaDto mediaDto) {
        return new Media(getImages(mediaDto.getImages()), getVideos(mediaDto.getVideos()));
    }

    private static List<Image> getImages(List<ImageDto> imageDtos) {
        return imageDtos.stream()
            .map(CategoryCreationRequestToCategoryConverter::getImage)
            .collect(Collectors.toList());
    }

    private static Image getImage(ImageDto imageDto) {
        return new Image(null, imageDto.getUrl(), imageDto.getName(), imageDto.getFormat(), imageDto.getCaption());
    }

    private static List<Video> getVideos(List<VideoDto> videoDtos) {
        return videoDtos.stream()
            .map(CategoryCreationRequestToCategoryConverter::getVideo)
            .collect(Collectors.toList());
    }

    private static Video getVideo(VideoDto videoDto) {
        return new Video(null, videoDto.getUrl(), videoDto.getName(), videoDto.getFormat());
    }
}

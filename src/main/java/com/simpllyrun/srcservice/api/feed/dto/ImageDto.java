package com.simpllyrun.srcservice.api.feed.dto;

import com.simpllyrun.srcservice.api.feed.domain.Image;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageDto {

    private Long id;
    private String originalFilename;
    private String storeFilename;

    public static ImageDto of(Image image){
        return ImageDto.builder()
                .id(image.getId())
                .originalFilename(image.getOriginalFilename())
                .storeFilename(image.getStoreFilename())
                .build();
    }
}

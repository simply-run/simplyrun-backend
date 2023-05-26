package com.simpllyrun.srcservice.api.feed.dto.mapper;

import com.simpllyrun.srcservice.api.feed.domain.Image;
import com.simpllyrun.srcservice.api.feed.dto.ImageDto;

public interface ImageDtoMapper {

    static Image toEntity(ImageDto imageDto){
        return Image.builder()
                .id(imageDto.getId())
                .originalFilename(imageDto.getOriginalFilename())
                .storeFilename(imageDto.getStoreFilename())
                .build();
    }
}

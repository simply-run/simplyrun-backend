package com.simpllyrun.srcservice.api.feed.dto.mapper;

import com.simpllyrun.srcservice.api.feed.domain.PostImage;
import com.simpllyrun.srcservice.api.feed.dto.PostImageDto;

public interface ImageDtoMapper {

    static PostImage toEntity(PostImageDto postImageDto){
        return PostImage.builder()
                .originalFilename(postImageDto.getOriginalFilename())
                .storeFilename(postImageDto.getStoreFilename())
                .postImageUrl(postImageDto.getPostImageUrl())
                .build();
    }
}

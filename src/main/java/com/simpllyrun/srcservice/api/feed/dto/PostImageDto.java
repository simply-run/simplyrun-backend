package com.simpllyrun.srcservice.api.feed.dto;

import com.simpllyrun.srcservice.api.feed.domain.PostImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "포스트 이미지 DTO")
public class PostImageDto {

    private Long id;
    private String originalFilename;
    private String storeFilename;
    private String postImageUrl;

    public static PostImageDto of(PostImage postImage){
        return PostImageDto.builder()
                .id(postImage.getId())
                .originalFilename(postImage.getOriginalFilename())
                .storeFilename(postImage.getStoreFilename())
                .postImageUrl(postImage.getPostImageUrl())
                .build();
    }
}

package com.simpllyrun.srcservice.api.dto.feed;

import com.simpllyrun.srcservice.api.domain.feed.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "포스트 DTO")
public class PostDto {

    private Long id;
    private String content;
    private Post.CategoryEnum category;

    public static PostDto of(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .content(post.getContent())
                .category(post.getCategory())
                .build();
    }
}

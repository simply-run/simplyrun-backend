package com.simpllyrun.srcservice.api.feed.dto.mapper;

import com.simpllyrun.srcservice.api.user.domain.User;
import com.simpllyrun.srcservice.api.feed.domain.Post;
import com.simpllyrun.srcservice.api.feed.dto.PostDto;

// 나중에.. Mapper 어노테이션 적용
public interface PostDtoMapper {
    static Post toEntity(PostDto postDto, User user) {
        return Post.builder()
                .categoryType(postDto.getCategory())
                .content(postDto.getContent())
                .user(user)
                .build();
    }
}

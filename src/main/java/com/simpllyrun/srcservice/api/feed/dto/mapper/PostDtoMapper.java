package com.simpllyrun.srcservice.api.feed.dto.mapper;

import com.simpllyrun.srcservice.api.feed.domain.Post;
import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import com.simpllyrun.srcservice.api.user.domain.User;


// 나중에.. Mapper 어노테이션 적용
public interface PostDtoMapper {
    static Post toEntity(PostDto.PostRequestDto postDto, User user) {
        return Post.builder()
                .categoryType(postDto.getCategory())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .user(user)
                .build();
    }
}

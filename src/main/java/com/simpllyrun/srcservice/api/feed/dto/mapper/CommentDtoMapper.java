package com.simpllyrun.srcservice.api.feed.dto.mapper;

import com.simpllyrun.srcservice.api.feed.domain.Comment;
import com.simpllyrun.srcservice.api.feed.domain.Post;
import com.simpllyrun.srcservice.api.feed.dto.CommentDto;
import com.simpllyrun.srcservice.api.user.domain.User;

public interface CommentDtoMapper {
    static Comment toEntity(CommentDto commentDto, User user, Post post){
        return Comment.builder()
                .content(commentDto.getContent())
                .user(user)
                .post(post)
                .build();
    }
}

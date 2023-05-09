package com.simpllyrun.srcservice.api.feed.service;

import com.simpllyrun.srcservice.api.feed.dto.PostDto;

public interface PostService {

    Long createPost(PostDto postDto);
    void deletePost(Long postId);
    void updatePost(Long postId, String content);
}

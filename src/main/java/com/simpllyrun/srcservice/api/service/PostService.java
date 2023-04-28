package com.simpllyrun.srcservice.api.service;

import com.simpllyrun.srcservice.api.dto.feed.PostDto;

public interface PostService {

    Long createPost(PostDto postDto);
    void deletePost(Long postId);
    void updatePost(Long postId, String content);
}

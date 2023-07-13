package com.simpllyrun.srcservice.api.feed.service.post;

import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostService {

    Long createPost(PostDto.PostRequestDto postDto);
    void deletePost(Long postId);
    void updatePost(Long postId, PostDto.PostRequestDto postDto);

    PostDto.PostResponseDto findPostById(Long postId);
    Page<PostDto.PostResponseDto> findAllByUserId(String userId, Pageable pageable);

    Page<PostDto.PostResponseDto> findAll(Pageable pageable);
}

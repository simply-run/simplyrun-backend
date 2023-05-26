package com.simpllyrun.srcservice.api.feed.service;

import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    Long createPost(PostDto postDto, List<MultipartFile> multipartFiles);
    void deletePost(Long postId);
    void updatePost(Long postId, String content, List<MultipartFile> multipartFiles);
}

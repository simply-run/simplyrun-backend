package com.simpllyrun.srcservice.api.feed.service.post;

import com.simpllyrun.srcservice.api.feed.domain.Post;
import com.simpllyrun.srcservice.api.feed.dto.PostCreateDto;
import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    Long createPost(PostCreateDto postDto, List<MultipartFile> multipartFiles);
    void deletePost(Long postId);
    void updatePost(Long postId, String content, List<MultipartFile> multipartFiles);

    PostDto findPostById(Long postId);
    Page<Post> findAllByUserId(String userId, Pageable pageable);

    Page<Post> findAll(Pageable pageable);
}
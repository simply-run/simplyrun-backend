package com.simpllyrun.srcservice.api.feed.service;

import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import com.simpllyrun.srcservice.api.feed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {


    private final PostRepository postRepository;

    @Override
    public Long createPost(PostDto postDto) {

        return null;
    }

    @Override
    public void deletePost(Long postId) {

    }

    @Override
    public void updatePost(Long postId, String content) {

    }
}

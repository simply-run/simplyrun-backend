package com.simpllyrun.srcservice.api.service;

import com.simpllyrun.srcservice.api.domain.feed.Post;
import com.simpllyrun.srcservice.api.dto.feed.PostDto;
import com.simpllyrun.srcservice.api.repository.feed.PostRepository;
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

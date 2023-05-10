package com.simpllyrun.srcservice.api.feed.service;

import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import com.simpllyrun.srcservice.api.feed.dto.mapper.PostDtoMapper;
import com.simpllyrun.srcservice.api.feed.repository.PostRepository;
import com.simpllyrun.srcservice.api.repository.UserRepository;
import com.simpllyrun.srcservice.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long createPost(PostDto postDto) {
        // 이미지 저장 로직 추가 해야 함
        Long userId = AuthUtil.getAuthUserId();

        var user = userRepository.findById(userId)
                .orElseThrow(NoSuchElementException::new);

        var post = postRepository.save(PostDtoMapper.toEntity(postDto, user));

        return post.getId();
    }

    @Override
    public void deletePost(Long postId) {

    }

    @Override
    public void updatePost(Long postId, String content) {

    }
}

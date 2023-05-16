package com.simpllyrun.srcservice.api.feed.service;

import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import com.simpllyrun.srcservice.api.feed.dto.mapper.PostDtoMapper;
import com.simpllyrun.srcservice.api.feed.repository.PostRepository;
import com.simpllyrun.srcservice.api.user.repository.UserRepository;
import com.simpllyrun.srcservice.global.util.AuthUtil;
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
    @Transactional
    public void deletePost(Long postId) {
        var post = postRepository.findById(postId).orElseThrow(()-> new IllegalArgumentException("해당 게시물은 존재하지 않습니다."));

        postRepository.delete(post);
    }

    @Override
    @Transactional
    public void updatePost(Long postId, String content) {
        var post = postRepository.findById(postId).orElseThrow(()-> new IllegalArgumentException("해당 게시물은 존재하지 않습니다."));

        post.update(content);
        postRepository.save(post);
    }
}

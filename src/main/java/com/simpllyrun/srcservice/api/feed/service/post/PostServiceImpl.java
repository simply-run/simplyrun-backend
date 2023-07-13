package com.simpllyrun.srcservice.api.feed.service.post;

import com.simpllyrun.srcservice.api.feed.domain.Post;
import com.simpllyrun.srcservice.api.feed.domain.PostImage;
import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import com.simpllyrun.srcservice.api.feed.dto.PostImageDto;
import com.simpllyrun.srcservice.api.feed.dto.mapper.ImageDtoMapper;
import com.simpllyrun.srcservice.api.feed.dto.mapper.PostDtoMapper;
import com.simpllyrun.srcservice.api.feed.repository.PostImageRepository;
import com.simpllyrun.srcservice.api.feed.repository.post.PostRepository;
import com.simpllyrun.srcservice.api.feed.service.postImage.PostImageService;
import com.simpllyrun.srcservice.api.user.repository.UserRepository;
import com.simpllyrun.srcservice.global.error.SrcException;
import com.simpllyrun.srcservice.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.simpllyrun.srcservice.global.error.ErrorCode.INPUT_VALUE_INVALID;
import static com.simpllyrun.srcservice.global.error.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostImageService postImageService;
    private final PostImageRepository postImageRepository;

    @Override
    @Transactional
    public Long createPost(PostDto.PostRequestDto postDto) {

        List<PostImage> postImages = new ArrayList<>();
        Long userId = AuthUtil.getAuthUserId();
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new SrcException(USER_NOT_FOUND));

        var post = postRepository.save(PostDtoMapper.toEntity(postDto, user));
        post.updateImage(postImages);

        // 이미지 저장 로직 추가
        if (postDto.getMultipartFiles() != null) {
            List<PostImageDto> postImageDtoList = postImageService.uploadImage(postDto.getMultipartFiles());
            for (PostImageDto postImageDto : postImageDtoList) {
                PostImage postImage = ImageDtoMapper.toEntity(postImageDto);
                postImage.setPost(post);
                postImageRepository.save(postImage);
                postImages.add(postImage);
            }
        }
        return post.getId();
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        var post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException(String.valueOf(INPUT_VALUE_INVALID)));

        postRepository.delete(post);
    }

    @Override
    @Transactional
    public void updatePost(Long postId, PostDto.PostRequestDto postDto) {
        var post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException(String.valueOf(INPUT_VALUE_INVALID)));
        List<PostImage> postImages = post.getPostImages();

        if (postDto.getMultipartFiles() != null) {
            List<PostImageDto> postImageDtoList = postImageService.uploadImage(postDto.getMultipartFiles());
            for (PostImageDto postImageDto : postImageDtoList) {
                PostImage postImage = ImageDtoMapper.toEntity(postImageDto);
                postImages.add(postImage);
            }
        }

        post.updateImage(postImages);
        post.updateText(postDto.getTitle(), postDto.getContent());
        postRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public PostDto.PostResponseDto findPostById(Long postId) {
        Post findPost = postRepository.findByIdFetchJoin(postId);
        return PostDto.PostResponseDto.of(findPost);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDto.PostResponseDto> findAllByUserId(String userId, Pageable pageable) {
        userRepository.findByUserId(userId)
                .orElseThrow(() -> new SrcException(USER_NOT_FOUND));
        Page<Post> postPage = postRepository.findAllByUserId(userId, pageable);
        return postPage.map(PostDto.PostResponseDto::of);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDto.PostResponseDto> findAll(Pageable pageable) {
        Long userIdentity = AuthUtil.getAuthUserId();
        return postRepository.findPostDtoOfFollowingByUserIdentity(userIdentity, pageable);
    }


}

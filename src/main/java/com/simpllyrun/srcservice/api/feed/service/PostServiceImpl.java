package com.simpllyrun.srcservice.api.feed.service;

import com.simpllyrun.srcservice.api.feed.domain.Image;
import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import com.simpllyrun.srcservice.api.feed.dto.ImageDto;
import com.simpllyrun.srcservice.api.feed.dto.mapper.ImageDtoMapper;
import com.simpllyrun.srcservice.api.feed.dto.mapper.PostDtoMapper;
import com.simpllyrun.srcservice.api.feed.repository.PostRepository;
import com.simpllyrun.srcservice.api.user.repository.UserRepository;
import com.simpllyrun.srcservice.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    @Override
    public Long createPost(PostDto postDto, List<MultipartFile> multipartFiles) {

        List<Image> postImages = new ArrayList<>();
        Long userId = AuthUtil.getAuthUserId();

        // 이미지 저장 로직 추가
        if (multipartFiles != null){
            List<ImageDto> imageDtoList = imageService.uploadImage(multipartFiles);
            for (ImageDto imageDto : imageDtoList) {
                Image image = ImageDtoMapper.toEntity(imageDto);
                postImages.add(image);
            }
        }
        var user = userRepository.findById(userId)
                .orElseThrow(NoSuchElementException::new);

        var post = postRepository.save(PostDtoMapper.toEntity(postDto, user, postImages));

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
    public void updatePost(Long postId, String content, List<MultipartFile> multipartFiles) {
        var post = postRepository.findById(postId).orElseThrow(()-> new IllegalArgumentException("해당 게시물은 존재하지 않습니다."));
        List<Image> postImages = post.getPostImages();

        if (multipartFiles != null){
            List<ImageDto> imageDtoList = imageService.uploadImage(multipartFiles);
            for (ImageDto imageDto : imageDtoList) {
                Image image = ImageDtoMapper.toEntity(imageDto);
                postImages.add(image);
            }
        }

        post.updateImage(postImages);
        post.update(content);
        postRepository.save(post);
    }
}

package com.simpllyrun.srcservice.api.feed.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpllyrun.srcservice.api.feed.domain.Post;
import com.simpllyrun.srcservice.api.feed.dto.PostCreateDto;
import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import com.simpllyrun.srcservice.api.feed.repository.PostRepositoryCustom;
import com.simpllyrun.srcservice.api.feed.service.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Tag(name = "posts", description = "게시글 API")
public class PostController {

    private final PostService postService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "게시글 작성")
    public ResponseEntity<Long> addPost(@RequestPart(value = "dto") @Valid PostCreateDto postDto,
                                        @RequestPart(value = "images", required = false) List<MultipartFile> multipartFiles){
        Long postId = postService.createPost(postDto, multipartFiles);
        if (postId == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(postId);
        }
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId){
        postService.deletePost(postId);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{postId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "게시글 수정")
    public ResponseEntity<Void> updatePost(@PathVariable Long postId, @RequestPart(value = "dto") PostDto postDto, @RequestPart(value = "images", required = false) List<MultipartFile> multipartFiles){
        postService.updatePost(postId, postDto.getContent(), multipartFiles);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}")
    @Operation(summary = "게시글 조회")
    public ResponseEntity<PostDto> findPost(@Parameter(name = "postId", description = "post의 id")
                                                @PathVariable Long postId){

        PostDto postDto = postService.findPostById(postId);

        if (postId == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(postDto);
        }
    }

    @GetMapping("/list/{userId}")
    @Operation(summary = "userId의 게시글 전체 조회")
    public ResponseEntity<Page<PostDto>> findAllPostByUserId(@Parameter(name = "userId", description = "사용자 아이디,, 예)invigorating92 ")
                                                                 @PathVariable("userId") String userId,
                                                             @PageableDefault(page = 0, size = 10) Pageable pageable){

        Page<Post> findPostPage = postService.findAllByUserId(userId, pageable);
        Page<PostDto> postDtoPage = findPostPage.map(post -> PostDto.of(post));

        return ResponseEntity.ok(postDtoPage);
    }

    @GetMapping("/list")
    @Operation(summary = "게시글 전체 조회")
    public ResponseEntity<Page<PostDto>> findAllPost(@PageableDefault(page = 0, size = 10) Pageable pageable){

        Page<Post> findAll = postService.findAll(pageable);
        Page<PostDto> postDtoPage = findAll.map(post -> PostDto.of(post));

        return ResponseEntity.ok(postDtoPage);
    }

}

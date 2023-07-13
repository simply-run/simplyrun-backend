package com.simpllyrun.srcservice.api.feed.controller;

import com.simpllyrun.srcservice.api.feed.domain.Post;
import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import com.simpllyrun.srcservice.api.feed.service.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Tag(name = "posts", description = "게시글 API")
public class PostController {

    private final PostService postService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "게시글 작성")
    public ResponseEntity<Long> addPost(@Valid @ModelAttribute PostDto.PostRequestDto postDto) {
        Long postId = postService.createPost(postDto);
        if (postId == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(postId);
        }
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<Void> deletePost(@PathVariable @Parameter(name = "postId", description = "삭제할 게시글의 id값") Long postId) {
        postService.deletePost(postId);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{postId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "게시글 수정")
    public ResponseEntity<Void> updatePost(@PathVariable @Parameter(name = "postId", description = "수정할 게시글의 id값") Long postId,
                                           @Valid @ModelAttribute PostDto.PostRequestDto postDto) {
        postService.updatePost(postId, postDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}")
    @Operation(summary = "게시글 조회")
    public ResponseEntity<PostDto.PostResponseDto> findPost(@PathVariable @Parameter(name = "postId", description = "조회할 게시글의 id") Long postId) {

        PostDto.PostResponseDto postDto = postService.findPostById(postId);

        if (postId == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(postDto);
        }
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "userId의 게시글 전체 조회")
    @ResponseStatus(HttpStatus.OK)
    public Page<PostDto.PostResponseDto> findAllPostByUserId(@Parameter(name = "userId", description = "사용자 아이디,, 예)invigorating92 ")
                                                                             @PathVariable("userId") String userId,
                                                                             @PageableDefault(page = 0, size = 10) @Parameter(name = "pageable", hidden = true) Pageable pageable) {
        return postService.findAllByUserId(userId, pageable);
    }

    @GetMapping
    @Operation(summary = "게시글 전체 조회")
    @ResponseStatus(HttpStatus.OK)
    public Page<PostDto.PostResponseDto> findAllPost(@PageableDefault(page = 0, size = 10)
                                                         @Parameter(name = "pageable", hidden = true)
                                                         Pageable pageable){
        return postService.findAll(pageable);
    }

}

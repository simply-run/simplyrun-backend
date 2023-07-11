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
    @Operation(summary = "게시글 작성", description = "스웨거에선 작동x , 포스트맨에서는 작동o")
    public ResponseEntity<Long> addPost(@RequestPart(value = "dto") @Valid @Parameter(name = "postDto", description = "게시글 생성에 필요한 내용") PostDto.PostRequestDto postDto,
                                        @RequestPart(value = "multipartFiles", required = false) @Parameter(name = "multipartFiles", description = "사진이나 동영상") List<MultipartFile> multipartFiles){
        Long postId = postService.createPost(postDto, multipartFiles);
        if (postId == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(postId);
        }
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<Void> deletePost(@PathVariable @Parameter(name = "postId", description = "삭제할 게시글의 id값") Long postId){
        postService.deletePost(postId);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{postId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "게시글 수정", description = "스웨거에선 작동x , 포스트맨에서는 작동o")
    public ResponseEntity<Void> updatePost(@PathVariable @Parameter(name = "postId", description = "수정할 게시글의 id값") Long postId,
                                           @RequestPart(value = "dto") @Valid @Parameter(name = "postDto", description = "게시글 수정에 필요한 내용") PostDto.PostRequestDto postDto,
                                           @RequestPart(value = "multipartFiles", required = false) @Parameter(name = "multipartFiles", description = "사진이나 동영상") List<MultipartFile> multipartFiles){
        postService.updatePost(postId, postDto, multipartFiles);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}")
    @Operation(summary = "게시글 조회")
    public ResponseEntity<PostDto.PostResponseDto> findPost(@PathVariable @Parameter(name = "postId", description = "조회할 게시글의 id") Long postId){

        PostDto.PostResponseDto postDto = postService.findPostById(postId);

        if (postId == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(postDto);
        }
    }

    @GetMapping("/list/{userId}")
    @Operation(summary = "userId의 게시글 전체 조회")
    public ResponseEntity<Page<PostDto.PostResponseDto>> findAllPostByUserId(@Parameter(name = "userId", description = "사용자 아이디,, 예)invigorating92 ")
                                                                 @PathVariable("userId") String userId,
                                                             @PageableDefault(page = 0, size = 10) @Parameter(name = "pageable", hidden = true) Pageable pageable){

        Page<Post> findPostPage = postService.findAllByUserId(userId, pageable);
        Page<PostDto.PostResponseDto> postDtoPage = findPostPage.map(post -> PostDto.PostResponseDto.of(post));

        return ResponseEntity.ok(postDtoPage);
    }

    @GetMapping("/list")
    @Operation(summary = "게시글 전체 조회")
    public ResponseEntity<Page<PostDto.PostResponseDto>> findAllPost(@PageableDefault(page = 0, size = 10) @Parameter(name = "pageable", hidden = true) Pageable pageable){

        Page<Post> findAll = postService.findAll(pageable);
        Page<PostDto.PostResponseDto> postDtoPage = findAll.map(post -> PostDto.PostResponseDto.of(post));

        return ResponseEntity.ok(postDtoPage);
    }

}

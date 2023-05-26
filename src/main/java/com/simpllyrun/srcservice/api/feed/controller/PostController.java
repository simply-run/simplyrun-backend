package com.simpllyrun.srcservice.api.feed.controller;

import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import com.simpllyrun.srcservice.api.feed.service.ImageService;
import com.simpllyrun.srcservice.api.feed.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "게시글 작성")
    public ResponseEntity<Long> addPost(@RequestPart(value = "dto") @Valid PostDto postDto, @RequestPart(value = "images", required = false) List<MultipartFile> multipartFiles) {
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

    @PatchMapping(value = "/{postId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "게시글 수정")
    public ResponseEntity<Void> updatePost(@PathVariable Long postId, @RequestPart(value = "dto") PostDto postDto, @RequestPart(value = "images", required = false) List<MultipartFile> multipartFiles){
        postService.updatePost(postId, postDto.getContent(), multipartFiles);

        return ResponseEntity.ok().build();
    }


}

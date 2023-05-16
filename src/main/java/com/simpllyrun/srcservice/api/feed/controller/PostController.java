package com.simpllyrun.srcservice.api.feed.controller;

import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import com.simpllyrun.srcservice.api.feed.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Tag(name = "posts", description = "게시글 API")
public class PostController {

    private final PostService postService;

    @PostMapping
    @Operation(summary = "게시글 작성")
    public ResponseEntity<Long> addPost(@RequestBody @Valid PostDto postDto) {
        Long postId = postService.createPost(postDto);
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

    @PatchMapping("/{postId}")
    @Operation(summary = "게시글 수정")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody PostDto postDto){
        postService.updatePost(postId, postDto.getContent());

        return ResponseEntity.ok().build();
    }


}

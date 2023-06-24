package com.simpllyrun.srcservice.api.feed.controller;

import com.simpllyrun.srcservice.api.feed.domain.Comment;
import com.simpllyrun.srcservice.api.feed.dto.CommentDto;
import com.simpllyrun.srcservice.api.feed.service.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@Tag(name = "comments", description = "댓글 API")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @Operation(summary = "댓글 작성")
    public ResponseEntity<Long> addComment(@RequestParam Long postId, @RequestBody @Valid CommentDto commentDto){
        Long commentId = commentService.addComment(postId, commentDto);

        if (commentId == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(commentId);
        }
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "댓글 수정")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId, @RequestBody CommentDto commentDto){
        commentService.updateComment(commentId, commentDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    @Operation(summary = "전체 댓글 조회")
    public ResponseEntity<Page<CommentDto>> findComment(@PageableDefault(page = 0, size = 10) Pageable pageable){
        Page<Comment> commentPage = commentService.findAll(pageable);
        Page<CommentDto> commentDtoPage = commentPage.map(c -> CommentDto.of(c));

        return ResponseEntity.ok(commentDtoPage);
    }

}

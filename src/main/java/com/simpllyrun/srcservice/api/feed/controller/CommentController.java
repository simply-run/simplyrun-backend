package com.simpllyrun.srcservice.api.feed.controller;

import com.simpllyrun.srcservice.api.feed.domain.Comment;
import com.simpllyrun.srcservice.api.feed.dto.CommentDto;
import com.simpllyrun.srcservice.api.feed.service.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    public ResponseEntity<Long> addComment(@RequestParam(required = true) @Parameter(name = "postId", description = "댓글 작성을 원하는 게시글의 postId값") Long postId,
                                           @RequestBody @Valid @Parameter(name = "commentDto", description = "댓글 작성에 필요한 내용") CommentDto commentDto){
        Long commentId = commentService.addComment(postId, commentDto);

        if (commentId == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(commentId);
        }
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<Void> deleteComment(@PathVariable @Parameter(name = "commendId", description = "삭제할 댓글의 id값") Long commentId){
        commentService.deleteComment(commentId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "댓글 수정")
    public ResponseEntity<Void> updateComment(@PathVariable @Parameter(name = "commendId", description = "수정할 댓글의 id값") Long commentId,
                                              @RequestBody @Parameter(name = "commentDto", description = "댓글 수정에 필요한 내용") CommentDto commentDto){
        commentService.updateComment(commentId, commentDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{commentId}")
    @Operation(summary = "댓글 단건 조회")
    public ResponseEntity<CommentDto> findComment(@PathVariable @Parameter(name = "commendId", description = "조회할 댓글의 id값") Long commentId){
        CommentDto commentDto = commentService.findCommentById(commentId);
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("/list/post/{postId}")
    @Operation(summary = "postId 게시글의 전체 댓글 조회")
    public ResponseEntity<Page<CommentDto>> findCommentByPostId(@PathVariable @Parameter(name = "postId", description = "댓글 조회할 게시글의 id값") Long postId,
                                                                @PageableDefault(page = 0, size = 10) @Parameter(name = "pageable", hidden = true) Pageable pageable){
        Page<Comment> commentPage = commentService.findAllByPostId(postId, pageable);
        Page<CommentDto> commentDtoPage = commentPage.map(c -> CommentDto.of(c));

        return ResponseEntity.ok(commentDtoPage);
    }

    @GetMapping("/list/user/{userId}")
    @Operation(summary = "userId 유저의 전체 댓글 조회")
    public ResponseEntity<Page<CommentDto>> findCommentByUserId(@PathVariable @Parameter(name = "userId", description = "댓글 조회할 user의 userId(String)") String userId,
                                                                @PageableDefault(page = 0, size = 10) @Parameter(name = "pageable", hidden = true) Pageable pageable){
        Page<Comment> commentPage = commentService.findAllByUserId(userId, pageable);
        Page<CommentDto> commentDtoPage = commentPage.map(c -> CommentDto.of(c));

        return ResponseEntity.ok(commentDtoPage);
    }

}

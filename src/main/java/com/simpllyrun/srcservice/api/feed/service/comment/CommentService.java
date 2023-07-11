package com.simpllyrun.srcservice.api.feed.service.comment;

import com.simpllyrun.srcservice.api.feed.domain.Comment;
import com.simpllyrun.srcservice.api.feed.dto.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    Long addComment(Long postId, CommentDto commentDto);
    void updateComment(Long commentId, CommentDto commentDto);
    void deleteComment(Long commentId);

    CommentDto findCommentById(Long commentId);

    Page<Comment> findAllByPostId(Long postId, Pageable pageable);

    Page<Comment> findAllByUserId(String userId, Pageable pageable);
}

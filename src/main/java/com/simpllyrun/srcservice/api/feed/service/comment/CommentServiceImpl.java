package com.simpllyrun.srcservice.api.feed.service.comment;

import com.simpllyrun.srcservice.api.feed.domain.Comment;
import com.simpllyrun.srcservice.api.feed.domain.Post;
import com.simpllyrun.srcservice.api.feed.dto.CommentDto;
import com.simpllyrun.srcservice.api.feed.repository.CommentRepository;
import com.simpllyrun.srcservice.api.feed.repository.post.PostRepository;
import com.simpllyrun.srcservice.api.user.repository.UserRepository;
import com.simpllyrun.srcservice.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    @Override
    @Transactional
    public Long addComment(Long postId, CommentDto commentDto) {

        Long userId = AuthUtil.getAuthUserId();
        var user = userRepository.findById(userId)
                .orElseThrow(NoSuchElementException::new);

        Post post = postRepository.findById(postId).orElseThrow(NoSuchElementException::new);

        Comment comment = Comment.builder()
                .user(user)
                .content(commentDto.getContent())
                .post(post)
                .build();

        return commentRepository.save(comment).getId();

    }

    @Override
    @Transactional
    public void updateComment(Long commentId, CommentDto commentDto) {
        var comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("해당 댓글은 존재하지 않습니다"));
        comment.updateContent(commentDto.getContent());
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        var comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("해당 댓글은 존재하지 않습니다"));
        commentRepository.delete(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto findCommentById(Long commentId) {
        var comment = commentRepository.findById(commentId).orElseThrow(()-> new NoSuchElementException("해당 댓글은 존재하지 않습니다"));
        return CommentDto.of(comment);
    }

    //특정 게시글의 전체 댓글
    @Override
    @Transactional(readOnly = true)
    public Page<Comment> findAllByPostId(Long postId, Pageable pageable) {
        postRepository.findById(postId).orElseThrow(()-> new NoSuchElementException("해당 게시글은 존재하지 않습니다"));
        Page<Comment> commentPage = commentRepository.findAllByPostId(postId, pageable);
        return commentPage;
    }

    //특정 유저의 전체 댓글
    @Override
    @Transactional(readOnly = true)
    public Page<Comment> findAllByUserId(String userId, Pageable pageable) {
        userRepository.findByUserId(userId).orElseThrow(()-> new NoSuchElementException("해당 유저는 존재하지 않습니다"));
        Page<Comment> commentPage = commentRepository.findAllByUserId(userId, pageable);
        return commentPage;
    }



}

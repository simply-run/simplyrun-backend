package com.simpllyrun.srcservice.api.feed.repository;

import com.simpllyrun.srcservice.api.feed.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select c from Comment c where c.post.id = :postId")
    Page<Comment> findAllByPostId(@Param(value = "postId") Long postId, Pageable pageable);

    @Query(value = "select c from Comment c where c.user.userId = :userId")
    Page<Comment> findAllByUserId(@Param(value = "userId") String userId, Pageable pageable);
}

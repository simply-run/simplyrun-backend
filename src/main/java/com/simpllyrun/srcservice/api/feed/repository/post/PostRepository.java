package com.simpllyrun.srcservice.api.feed.repository.post;

import com.simpllyrun.srcservice.api.feed.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface PostRepository extends JpaRepository<Post, Long> , PostRepositoryCustom {

      @Query(value = "select p from Post p where p.user.userId = :userId")
      Page<Post> findAllByUserId(@Param(value = "userId") String userId, Pageable pageable);
}

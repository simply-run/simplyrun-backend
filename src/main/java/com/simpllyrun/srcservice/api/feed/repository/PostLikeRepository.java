package com.simpllyrun.srcservice.api.feed.repository;

import com.simpllyrun.srcservice.api.feed.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}

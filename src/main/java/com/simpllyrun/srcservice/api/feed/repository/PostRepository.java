package com.simpllyrun.srcservice.api.feed.repository;

import com.simpllyrun.srcservice.api.feed.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

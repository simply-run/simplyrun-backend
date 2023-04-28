package com.simpllyrun.srcservice.api.repository.feed;

import com.simpllyrun.srcservice.api.domain.feed.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

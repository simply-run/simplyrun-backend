package com.simpllyrun.srcservice.api.feed.repository;

import com.simpllyrun.srcservice.api.feed.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}

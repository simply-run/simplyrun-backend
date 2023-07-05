package com.simpllyrun.srcservice.api.feed.repository.post;

import com.simpllyrun.srcservice.api.feed.domain.Post;


public interface PostRepositoryCustom {
    Post findByIdFetchJoin(Long postId);
}

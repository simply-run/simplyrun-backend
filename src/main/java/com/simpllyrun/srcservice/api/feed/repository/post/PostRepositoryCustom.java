package com.simpllyrun.srcservice.api.feed.repository.post;

import com.simpllyrun.srcservice.api.feed.domain.Post;
import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostRepositoryCustom {
    Post findByIdFetchJoin(Long postId);

    Page<PostDto.PostResponseDto> findPostDtoOfFollowingByUserIdentity(Long userIdentity , Pageable pageable);
}

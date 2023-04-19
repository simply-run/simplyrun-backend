package com.simpllyrun.srcservice.api.repository.follow;

import com.simpllyrun.srcservice.api.dto.follow.FollowerDto;

import java.util.List;

public interface FollowRepositoryCustom {
    List<FollowerDto> findFollowings(Long loginId, Long userId);

    List<FollowerDto> findFollowers(Long loginId, Long userId);
}


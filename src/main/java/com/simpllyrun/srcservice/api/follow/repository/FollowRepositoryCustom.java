package com.simpllyrun.srcservice.api.follow.repository;

import com.simpllyrun.srcservice.api.follow.dto.FollowerDto;

import java.util.List;

public interface FollowRepositoryCustom {
    List<FollowerDto> findFollowings(Long loginId, Long userId);

    List<FollowerDto> findFollowers(Long loginId, Long userId);
}


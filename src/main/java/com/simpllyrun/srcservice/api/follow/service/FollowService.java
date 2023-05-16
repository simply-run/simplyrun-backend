package com.simpllyrun.srcservice.api.follow.service;

import com.simpllyrun.srcservice.api.follow.dto.FollowerDto;

import java.util.List;

public interface FollowService {

    Long follow(Long followUserId);
    void unfollow(Long followId);
    List<FollowerDto> getFollowings(String targetUserId);
    List<FollowerDto> getFollowers(String targetUserId);
}

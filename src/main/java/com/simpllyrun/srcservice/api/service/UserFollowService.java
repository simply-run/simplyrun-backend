package com.simpllyrun.srcservice.api.service;

public interface UserFollowService {

    Long follow(Long fromUserId, Long toUserId);
    void unfollow(Long followId);
}

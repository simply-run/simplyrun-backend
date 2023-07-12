package com.simpllyrun.srcservice.api.follow.service;

import com.simpllyrun.srcservice.api.follow.domain.Follow;
import com.simpllyrun.srcservice.api.follow.dto.FollowerDto;
import com.simpllyrun.srcservice.api.follow.repository.FollowRepository;
import com.simpllyrun.srcservice.api.user.domain.User;
import com.simpllyrun.srcservice.api.user.repository.UserRepository;
import com.simpllyrun.srcservice.global.error.SrcException;
import com.simpllyrun.srcservice.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.simpllyrun.srcservice.global.error.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long follow(Long followUserId) {
        Long userId = AuthUtil.getAuthUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new SrcException(USER_NOT_FOUND));
        User followUser = userRepository.findById(followUserId)
                .orElseThrow(() -> new SrcException(USER_NOT_FOUND));

        var userFollow = Follow.builder()
                .followUser(user)
                .followUser(followUser)
                .build();

        userFollow = followRepository.save(userFollow);

        return userFollow.getId();
    }

    @Override
    @Transactional
    public void unfollow(Long followId) {
        followRepository.deleteById(followId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FollowerDto> getFollowings(String targetUserId) {
        Long userId = AuthUtil.getAuthUserId();

        User user = userRepository.findByUserId(targetUserId)
                .orElseThrow(() -> new SrcException(USER_NOT_FOUND));

        return followRepository.findFollowings(userId, user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FollowerDto> getFollowers(String targetUserId) {
        Long userId = AuthUtil.getAuthUserId();

        User user = userRepository.findByUserId(targetUserId)
                .orElseThrow(() -> new SrcException(USER_NOT_FOUND));

        return followRepository.findFollowers(userId, user.getId());
    }
}

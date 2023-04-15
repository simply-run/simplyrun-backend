package com.simpllyrun.srcservice.api.service;

import com.simpllyrun.srcservice.api.domain.User;
import com.simpllyrun.srcservice.api.domain.UserFollow;
import com.simpllyrun.srcservice.api.repository.UserFollowRepository;
import com.simpllyrun.srcservice.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserFollowServiceImpl implements UserFollowService {

    private final UserFollowRepository userFollowRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long follow(Long fromUserId, Long toUserId) {
        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(NoSuchElementException::new);
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(NoSuchElementException::new);

        var userFollow = UserFollow.builder()
                .toUser(toUser)
                .fromUser(fromUser)
                .build();

        userFollow = userFollowRepository.save(userFollow);

        return userFollow.getId() != null ? userFollow.getId() : null;
    }

    @Override
    @Transactional
    public void unfollow(Long followId) {
        userFollowRepository.deleteById(followId);
    }
}

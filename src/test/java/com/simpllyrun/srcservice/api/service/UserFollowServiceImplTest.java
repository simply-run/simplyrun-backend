package com.simpllyrun.srcservice.api.service;

import com.simpllyrun.srcservice.api.domain.User;
import com.simpllyrun.srcservice.api.repository.UserFollowRepository;
import com.simpllyrun.srcservice.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserFollowServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFollowRepository userFollowRepository;

    @Autowired
    private UserFollowService userFollowService;


    @BeforeEach
    public void setUp() {
        userFollowRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("팔로우 테스트")
    void followTest() {
        var toUser = User.builder()
                .userId("to")
                .build();

        var fromUser = User.builder()
                .userId("from")
                .build();
        userRepository.saveAll(List.of(toUser, fromUser));


        var followId = userFollowService.follow(toUser.getId(), fromUser.getId());

        assertThat(followId).isNotNull();
        assertThat(followId).isGreaterThan(0L);
    }

    @Test
    @DisplayName("언팔로우 테스트")
    void unfollowTest() {
        var toUser = User.builder()
                .userId("to")
                .build();

        var fromUser = User.builder()
                .userId("from")
                .build();
        userRepository.saveAll(List.of(toUser, fromUser));


        var followId = userFollowService.follow(toUser.getId(), fromUser.getId());
        userFollowService.unfollow(followId);
        var follow = userFollowRepository.findById(followId)
                .orElse(null);

        assertThat(follow).isNull();
    }

    @Test
    @DisplayName("팔로우 여부 테스트")
    void isFollowTest() {
    }

    @Test
    @DisplayName("팔로워 리스트 테스트")
    void getFollowerListTest() {
    }

    @Test
    @DisplayName("팔로잉 리스트 테스트")
    void getFollowingListTest() {
    }
}
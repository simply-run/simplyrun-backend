package com.simpllyrun.srcservice.api.service;

import com.simpllyrun.srcservice.api.domain.User;
import com.simpllyrun.srcservice.api.domain.Follow;
import com.simpllyrun.srcservice.api.dto.follow.FollowerDto;
import com.simpllyrun.srcservice.api.dto.user.UserDto;
import com.simpllyrun.srcservice.api.repository.UserRepository;
import com.simpllyrun.srcservice.api.repository.follow.FollowRepository;
import com.simpllyrun.srcservice.config.TestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
//@Import(TestConfig.class)
class FollowServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private FollowRepository followRepository;

    @Autowired
    private FollowService followService;

    @Test
    @WithMockUser(username = "1")
    @DisplayName("팔로우 테스트")
    void followTest() {
        // given
        var toUser = User.builder()
                .build();
        var result = Follow.builder()
                .id(1L)
                .build();
        given(userRepository.findById(anyLong())).willReturn(Optional.of(toUser));
        given(followRepository.save(any())).willReturn(result);

        // when
        var followId = followService.follow(anyLong());

        // then
        assertThat(followId).isNotNull();
        assertThat(followId).isEqualTo(1L);
    }

    @Test
    @WithMockUser(username = "1")
    @DisplayName("언팔로우 테스트")
    void unfollowTest() {
        // given
        var toUser = User.builder()
                .build();
        var result = Follow.builder()
                .id(1L)
                .build();
        given(userRepository.findById(anyLong())).willReturn(Optional.of(toUser));
        given(followRepository.save(any())).willReturn(result);
        given(followRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        var followId = followService.follow(anyLong());
        followService.unfollow(followId);
        var follow = followRepository.findById(followId)
                .orElse(null);

        // then
        assertThat(follow).isNull();
    }

    @Test
    @WithMockUser(username = "1")
    @DisplayName("팔로잉 리스트 테스트")
    void getFollowingsTest() {
        // given
        var toUser = User.builder()
                .id(1L)
                .userId("to")
                .build();
        var result = List.of(FollowerDto.builder()
                .user(UserDto.builder().build())
                .isFollowing(true)
                .isFollower(true)
                .build());
        given(followRepository.findFollowings(anyLong(), anyLong())).willReturn(result);
        given(userRepository.findByUserId(anyString())).willReturn(Optional.of(toUser));

        // when
        var followers = followService.getFollowings(anyString());

        // then
        assertThat(followers).isNotNull();
        assertThat(followers.size()).isEqualTo(1);
    }

    @Test
    @WithMockUser(username = "1")
    @DisplayName("팔로워 리스트 테스트")
    void getFollowersTest() {
        // given
        var toUser = User.builder()
                .id(1L)
                .userId("to")
                .build();
        var result = List.of(FollowerDto.builder()
                .user(UserDto.builder().build())
                .isFollowing(true)
                .isFollower(true)
                .build());
        given(followRepository.findFollowers(anyLong(), anyLong())).willReturn(result);
        given(userRepository.findByUserId(anyString())).willReturn(Optional.of(toUser));

        // when
        var followers = followService.getFollowers(anyString());

        // then
        assertThat(followers).isNotNull();
        assertThat(followers.size()).isEqualTo(1);
    }
}
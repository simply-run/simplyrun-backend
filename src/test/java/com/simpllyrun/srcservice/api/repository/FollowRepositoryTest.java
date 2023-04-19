package com.simpllyrun.srcservice.api.repository;

import com.simpllyrun.srcservice.api.domain.Follow;
import com.simpllyrun.srcservice.api.domain.User;
import com.simpllyrun.srcservice.api.repository.follow.FollowRepository;
import com.simpllyrun.srcservice.config.TestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestConfig.class)
class FollowRepositoryTest {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void setUp() {
        followRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findByToUserIdTest() {
        var toUser = User.builder()
                .userId("to")
                .build();

        var fromUser = User.builder()
                .userId("from")
                .build();

        userRepository.saveAll(List.of(toUser,fromUser));

        var userFollow = Follow.builder()
                .user(toUser)
                .followUser(fromUser)
                .build();

        followRepository.save(userFollow);

        var userFollows = followRepository.findByUserId(toUser.getId());

        assertThat(userFollows.size()).isEqualTo(1);
    }

    @Test
    void findFollowingsTest() {
        // given
        var toUser = User.builder()
                .userId("to")
                .build();

        var fromUser = User.builder()
                .userId("from")
                .build();
        var userFollow = Follow.builder()
                .user(fromUser)
                .followUser(toUser)
                .build();

        // when
        userRepository.saveAll(List.of(toUser,fromUser));
        followRepository.save(userFollow);
        var followings= followRepository.findFollowings(toUser.getId(), fromUser.getId());

        // then
        assertThat(followings.size()).isEqualTo(1);
    }
}
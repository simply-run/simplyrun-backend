package com.simpllyrun.srcservice.api.repository;

import com.simpllyrun.srcservice.api.domain.User;
import com.simpllyrun.srcservice.api.domain.UserFollow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserFollowRepositoryTest {

    @Autowired
    private UserFollowRepository userFollowRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userFollowRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findByToUserId() {
        var toUser = User.builder()
                .userId("to")
                .build();

        var fromUser = User.builder()
                .userId("from")
                .build();

        userRepository.saveAll(List.of(toUser,fromUser));

        var userFollow = UserFollow.builder()
                .toUser(toUser)
                .fromUser(fromUser)
                .build();

        userFollowRepository.save(userFollow);

        var userFollows = userFollowRepository.findByToUserId(toUser.getId());

        assertThat(userFollows.size()).isEqualTo(1);
    }
}
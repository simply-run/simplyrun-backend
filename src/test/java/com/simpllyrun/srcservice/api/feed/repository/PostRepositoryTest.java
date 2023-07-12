package com.simpllyrun.srcservice.api.feed.repository;

import com.simpllyrun.srcservice.api.feed.domain.Post;
import com.simpllyrun.srcservice.api.feed.repository.post.PostRepository;
import com.simpllyrun.srcservice.api.follow.domain.Follow;
import com.simpllyrun.srcservice.api.follow.repository.FollowRepository;
import com.simpllyrun.srcservice.api.user.domain.User;
import com.simpllyrun.srcservice.api.user.repository.UserRepository;
import com.simpllyrun.srcservice.config.TestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestConfig.class)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findPostDtoOfFollowingByUserIdentityTest() {
        var toUser = User.builder()
                .userId("to")
                .build();

        var fromUser = User.builder()
                .userId("from")
                .build();

        userRepository.saveAll(List.of(toUser, fromUser));

        var userFollow = Follow.builder()
                .user(toUser)
                .followUser(fromUser)
                .build();

        followRepository.save(userFollow);

        postRepository.save(Post.builder()
                .user(fromUser)
                .title("title")
                .content("content")
                .build());

        PageRequest pageRequest = PageRequest.of(0, 10);

        var result = postRepository.findPostDtoOfFollowingByUserIdentity(toUser.getId(), pageRequest);

        assertThat(result.getTotalElements()).isEqualTo(1);
    }
}
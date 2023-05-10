package com.simpllyrun.srcservice.api.feed.service;

import com.simpllyrun.srcservice.api.user.domain.User;
import com.simpllyrun.srcservice.api.feed.domain.Post;
import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import com.simpllyrun.srcservice.api.feed.repository.PostRepository;
import com.simpllyrun.srcservice.api.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class PostServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Test
    @WithMockUser(username = "1")
    void createPost() {
        // given
        var toUser = User.builder()
                .build();
        var result = Post.builder()
                .id(1L)
                .build();
        var postDto = PostDto.builder()
                .content("test")
                .build();
        given(userRepository.findById(anyLong())).willReturn(Optional.of(toUser));
        given(postRepository.save(any())).willReturn(result);

        // when
        var postId = postService.createPost(postDto);

        // then
        assertThat(postId).isEqualTo(1L);
    }

    @Test
    void deletePost() {
    }

    @Test
    void updatePost() {
    }
}
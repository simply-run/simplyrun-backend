package com.simpllyrun.srcservice.api.feed.service;

import com.simpllyrun.srcservice.api.domain.User;
import com.simpllyrun.srcservice.api.feed.domain.Post;
import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import com.simpllyrun.srcservice.api.feed.repository.PostRepository;
import com.simpllyrun.srcservice.api.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
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
import static org.mockito.Mockito.*;

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
    @DisplayName("Post 생성 테스트")
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
    @WithMockUser(username = "1")
    @DisplayName("Post 삭제 테스트")
    void deletePost() {
        //given
        var post = Post.builder()
                .id(1L)
                .content("content")
                .build();

        var post2 = Post.builder().id(2L).content("content2").build(); //검증 오류 확인용 post1 생성

        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));

        // when
        postService.deletePost(post.getId());

        //then
        verify(postRepository).delete(post); //post2 넣으면 검증 오류 발생
    }


        @Test
        @WithMockUser(username = "1")
        @DisplayName("Post 수정 테스트")
        void updatePost(){
            //given
            Long postId = 1L;
            String content1 = "before change";

            var post = Post.builder()
                    .id(postId)
                    .content(content1)
                    .build();

            given(postRepository.save(any())).willReturn(post);
            given(postRepository.findById(anyLong())).willReturn(Optional.of(post));

            System.out.println("Before-update content = " + post.getContent());
            //when
            String content2 = "After change";
            postService.updatePost(postId, content2);
            //then
            verify(postRepository).save(post);
            assertThat(post.getContent()).isEqualTo(content2);
            System.out.println("After-update content = " + post.getContent());
        }

    }

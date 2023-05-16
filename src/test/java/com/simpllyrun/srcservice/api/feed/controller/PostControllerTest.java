package com.simpllyrun.srcservice.api.feed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpllyrun.srcservice.api.feed.domain.Post;
import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import com.simpllyrun.srcservice.api.feed.repository.PostRepository;
import com.simpllyrun.srcservice.api.user.domain.User;
import com.simpllyrun.srcservice.api.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        postRepository.deleteAll();

    }


    @DisplayName("post 생성하기")
    @Test
    @WithMockUser(username = "1")
    void addPost() throws Exception {
        //given
        final String url = "/api/posts";
        final String content1 = "content1";
        final String content2 = "content2";

        userRepository.save(User.builder()
                .build());

            //postDto 2개 생성
        PostDto postDto1 = PostDto.builder()
                .content(content1)
                .build();

        PostDto postDto2 = PostDto.builder()
                .content(content2)
                .build();

            //postDto를 requestBody로 변환
        String requestBody1 = objectMapper.writeValueAsString(postDto1);
        String requestBody2 = objectMapper.writeValueAsString(postDto2);

        //when
        ResultActions result1 = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody1));

        ResultActions result2 = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody2));

        //then
        result1.andExpect(status().isOk());
        result2.andExpect(status().isOk());

        //verify
        List<Post> posts = postRepository.findAll();

        assertThat(posts.size()).isEqualTo(2); // post 전체 개수
        assertThat(posts.get(0).getId()).isEqualTo(1); // post의 id값 확인
        assertThat(posts.get(0).getContent()).isEqualTo(content1); //postDto1 content 확인

        assertThat(posts.get(1).getId()).isEqualTo(2); //post의 id값 확인
        assertThat(posts.get(1).getContent()).isEqualTo(content2); //postDto2 content 확인
    }

    @DisplayName("Post 삭제하기")
    @Test
    @WithMockUser(username = "1")
    void deletePost() throws Exception {
        //given
        final String url = "/api/posts/{postId}";
        final String content = "content";

        Post post = Post.builder()
                .content(content)
                .build();

        postRepository.save(post);

        //when
        ResultActions result = mockMvc.perform(delete(url, post.getId()));

        //then
        result.andExpect(status().isOk());

        //verify
        Optional<Post> findPost = postRepository.findById(post.getId());

        System.out.println("findPost = " + findPost);
        assertThat(findPost).isEmpty();

        assertThat(postRepository.existsById(post.getId())).isFalse();
    }

    @DisplayName("Post 수정하기")
    @Test
    @WithMockUser(username = "1")
    void updatePost() throws Exception {
        //given
        final String url = "/api/posts/{id}";
        final String content = "content";
        final String updateContent = "update content";

        Post post = Post.builder()
                .content(content).build();

        postRepository.save(post);

        PostDto postDto = PostDto.builder()
                .content(updateContent)
                .build();

        String requestBody = objectMapper.writeValueAsString(postDto);

        //when
        ResultActions result = mockMvc.perform(patch(url, post.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(status().isOk());

        //verify
        Post findPost = postRepository.findById(post.getId()).get();

        System.out.println("findPost content = " + findPost.getContent());
        assertThat(findPost.getContent()).isEqualTo(updateContent);
    }
}
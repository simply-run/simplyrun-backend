package com.simpllyrun.srcservice.api.feed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpllyrun.srcservice.api.feed.domain.Comment;
import com.simpllyrun.srcservice.api.feed.domain.Post;
import com.simpllyrun.srcservice.api.feed.dto.CommentDto;
import com.simpllyrun.srcservice.api.feed.repository.CommentRepository;
import com.simpllyrun.srcservice.api.feed.service.comment.CommentService;
import com.simpllyrun.srcservice.api.user.domain.User;
import com.simpllyrun.srcservice.api.user.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CommentService commentService;
    @MockBean
    private CommentRepository commentRepository;

    private static final String URL = "/api/comments";

    @Test
    @WithMockUser(username = "1")
    @DisplayName("댓글 생성 테스트")
    void 댓글추가() throws Exception {
        //given
        var user = User.builder()
                .id(1L)
                .name("김현우")
                .userId("khw4756")
                .build();

        CommentDto commentDto = CommentDto.builder()
                .content("content").user(UserDto.of(user)).build();

        String requestBody = objectMapper.writeValueAsString(commentDto);

        given(commentService.addComment(anyLong(), any())).willReturn(1L);
        //when
        ResultActions result = mockMvc.perform(post(URL)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("postId", "1")
                .with(csrf()));

        //then
        result.andExpect(status().isOk())
                .andDo(print());

    }



    @Test
    @WithMockUser
    @DisplayName("댓글 삭제 테스트")
    void 댓글삭제() throws Exception {
        //given
        Comment comment = Comment.builder().id(1L).content("content").build();

        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));

        //when
        ResultActions result = mockMvc.perform(delete(URL + "/{commentId}", 1L).with(csrf()));
        //then
        result.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser
    @DisplayName("댓글 수정 테스트")
    void 댓글수정() throws Exception {
        //given
        Comment comment = Comment.builder().id(1L).content("before change").build();
        CommentDto commentDto = CommentDto.builder().content("after change").build();

        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));
        String requestBody = objectMapper.writeValueAsString(commentDto);

        //when
        ResultActions result = mockMvc.perform(put(URL + "/{commentId}", 1L)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()));

        //then
        result.andExpect(status().isOk())
                .andDo(print());
    }

    @Nested
    @DisplayName("댓글 전체 조회 테스트")
    class 댓글_전체_조회 {

        User user = User.builder().id(1L).userId("khw").build();
        Post post = Post.builder().id(1L).content("content").build();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Comment comment1 = Comment.builder().id(1L).content("comment1").user(user).post(post).build();
        Comment comment2 = Comment.builder().id(2L).content("comment2").user(user).post(post).build();
        Comment comment3 = Comment.builder().id(3L).content("comment3").user(user).post(post).build();
        List<Comment> commentList = new ArrayList<>();

        @Test
        @WithMockUser
        @DisplayName("postId 게시글의 전체 댓글 조회 테스트")
        void 특정게시글_댓글_전체조회() throws Exception {
            //given
            commentList.add(comment1);
            commentList.add(comment2);
            commentList.add(comment3);

            PageImpl<Comment> commentPage = new PageImpl<>(commentList, pageRequest, commentList.size());
            given(commentService.findAllByPostId(1L, pageRequest)).willReturn(commentPage);

            //when
            ResultActions result = mockMvc.perform(get(URL + "/list/post/{postId}", 1L, pageRequest));


            //then
            result.andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        @WithMockUser
        @DisplayName("userId 유저의 전체 댓글 조회")
        void 유저_게시글_댓글_전체조회() throws Exception {
            //given
            commentList.add(comment1);
            commentList.add(comment2);
            commentList.add(comment3);

            PageImpl<Comment> commentPage = new PageImpl<>(commentList, pageRequest, commentList.size());

            given(commentService.findAllByUserId("khw", pageRequest)).willReturn(commentPage);
            //when
            ResultActions result = mockMvc.perform(get(URL + "/list/user/{userId}", "khw", pageRequest));

            //then
            result.andExpect(status().isOk())
                    .andDo(print());

        }

    }



}
package com.simpllyrun.srcservice.api.controller.follow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpllyrun.srcservice.api.dto.follow.FollowerDto;
import com.simpllyrun.srcservice.api.dto.user.UserDto;
import com.simpllyrun.srcservice.api.service.FollowService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest(FollowController.class)
class FollowControllerTest {

    @MockBean
    private FollowService followService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static final String URL = "/api/follows";

    @Test
    @WithMockUser
    @DisplayName("컨트롤러 팔로우 성공")
    void followTest() throws Exception {
        // given
        given(followService.follow(anyLong()))
                .willReturn(1L);

        // when
        var resultActions = mvc.perform(post(URL + "/" + 1L)
                        .with(csrf()))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("1"))
                .andDo(print());
    }

    @Test
    @WithMockUser
    @DisplayName("컨트롤러 언팔로우 성공")
    void unfollowTest() throws Exception {
        // given
        doNothing().when(followService).unfollow(anyLong());

        // when
        var resultActions = mvc.perform(delete(URL + "/" + 1L)
                        .with(csrf()))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser
    @DisplayName("팔로잉 목록 조회")
    void getFollowingsTest() throws Exception {
        // given
        var result = List.of(FollowerDto.builder()
                .isFollower(true)
                .isFollowing(true)
                .user(UserDto.builder().build())
                .build());
        given(followService.getFollowings(anyString()))
                .willReturn(result);

        // when
        var resultActions = mvc.perform(get(URL + "/" + "1"+"/followings")
                        .with(csrf()))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andDo(print());
    }

    @Test
    @WithMockUser
    @DisplayName("팔로워 목록 조회")
    void getFollowersTest() throws Exception {
        // given
        given(followService.getFollowings(anyString()))
                .willReturn(any());

        // when
        var resultActions = mvc.perform(get(URL + "/" + "1"+"/followers")
                        .with(csrf()))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andDo(print());
    }
}
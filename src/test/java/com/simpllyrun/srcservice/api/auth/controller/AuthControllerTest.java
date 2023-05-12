package com.simpllyrun.srcservice.api.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpllyrun.srcservice.api.auth.controller.AuthController;
import com.simpllyrun.srcservice.api.auth.dto.AuthDto;
import com.simpllyrun.srcservice.api.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @MockBean
    private AuthService authService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static final String URL = "/api/auth";


    @Test
    @WithMockUser // 401 에러 해결
    void loginTest() throws Exception {
        var userDto = AuthDto.builder()
                .userId("아이디")
                .password("비밀번호")
                .build();
        var body = objectMapper.writeValueAsString(userDto);


        given(authService.login(any()))
                .willReturn("token");

        mvc.perform(post(URL + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .with(csrf())) // 403 에러 해결
                .andExpect(status().isOk());
    }
}
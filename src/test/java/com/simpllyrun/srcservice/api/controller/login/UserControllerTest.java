package com.simpllyrun.srcservice.api.controller.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpllyrun.srcservice.api.controller.user.UserController;
import com.simpllyrun.srcservice.api.domain.User;
import com.simpllyrun.srcservice.api.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static final String URL = "/api/users";


    @Test
    @WithMockUser
    void getUserTest() throws Exception {
        User user = User.builder()
                .userId("아이디")
                .name("이름")
                .build();

        given(userService.getUser())
                .willReturn(user);

        mvc.perform(get(URL + "/")
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}
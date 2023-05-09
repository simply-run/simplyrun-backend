package com.simpllyrun.srcservice.api.controller.user;

import com.simpllyrun.srcservice.api.domain.User;
import com.simpllyrun.srcservice.api.repository.UserRepository;
import com.simpllyrun.srcservice.api.service.CustomOAuth2UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@WebMvcTest(NaverLoginController.class)
class NaverLoginControllerTest {

    @MockBean
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
//    @WithMockUser(roles = "USER")
    void loginTest(){
        // OAuth2UserRequest가 필요하다

//        mockMvc.perform(MockMvcRequestBuilders.get("/")
//                        .accept(MediaType.APPLICATION_JSON)
//                        ).andDo()
//                .andExpect()

//        OAuth2User oAuth2User = customOAuth2UserService.loadUser();


//        mockMvc.perform()
    }

    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,100).forEach(i->{
            User user = User.builder()
                    .email("user" + i + "@naver.com")
                    .name("사용자" + i)
                    .build();

            //default role
            user.changeRole(User.Role.USER);

            userRepository.save(user);
        });

        Optional<User> byEmail = userRepository.findByEmail("user1@naver.com");
        User user = byEmail.get();
        Assertions.assertEquals(user.getEmail(), "user1@naver.com");
    }

    @Test
    @DisplayName("인증 토큰 발급 받는 테스트")
    void getToken() throws Exception {
        //given
        String username = "aaa@naver.com";
        String name = "khw";



        mockMvc.perform(post("/api/users/oauth/token")
                .param("email", username)
                .param("name", name)
        ).andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("access_token").exists());
    }


}
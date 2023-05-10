package com.simpllyrun.srcservice.api.auth.controller;

import com.simpllyrun.srcservice.api.auth.domain.RoleType;
import com.simpllyrun.srcservice.api.user.controller.NaverLoginController;
import com.simpllyrun.srcservice.api.user.domain.User;
import com.simpllyrun.srcservice.api.user.repository.UserRepository;
import com.simpllyrun.srcservice.api.auth.service.OAuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(NaverLoginController.class)
class NaverLoginControllerTest {

    @MockBean
    private OAuthService OAuthService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

//    @Test
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

//    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,100).forEach(i->{
            User user = User.builder()
                    .email("user" + i + "@naver.com")
                    .name("사용자" + i)
                    .build();

            //default role
            user.changeRole(RoleType.USER);

            userRepository.save(user);
        });

        Optional<User> byEmail = userRepository.findByEmail("user1@naver.com");
        User user = byEmail.get();
        Assertions.assertEquals(user.getEmail(), "user1@naver.com");
    }

//    @Test
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
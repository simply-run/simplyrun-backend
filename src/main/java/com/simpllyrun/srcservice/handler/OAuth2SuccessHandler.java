package com.simpllyrun.srcservice.handler;

import com.simpllyrun.srcservice.api.jwt.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Success Handler에 진입했다는 것은, 로그인이 완료되었다는 뜻이다.
 * 이 때가 정말 중요하다.
 *
 * 해당 클래스의 주요 기능은 크게 2가지이다.
 *
 * 1. 최초 로그인인지 확인
 * 2. Access Token, Refresh Token 생성 및 발급
 * 3. token을 포함하여 리다이렉트
 */
@Log4j2
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("-----------------onAuthenticationSuccess-----------------");
        log.info("authentication ={}", authentication);

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("oAuth2User = {}", oAuth2User);

        String email = (String) oAuth2User.getAttributes().get("email");
        log.info("email= {}", email);

        log.info("============토큰 발행 시작===========");
        String token = JwtTokenProvider.generateToken(email);
        JwtTokenProvider.sendAccessToken(response, token);

        log.info("token = {}", token);

        response.sendRedirect("/");
    }
}

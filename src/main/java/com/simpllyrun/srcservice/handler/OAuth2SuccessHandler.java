package com.simpllyrun.srcservice.handler;

import com.simpllyrun.srcservice.api.auth.domain.ProviderType;
import com.simpllyrun.srcservice.api.auth.domain.RoleType;
import com.simpllyrun.srcservice.api.auth.domain.UserPrincipal;
import com.simpllyrun.srcservice.api.auth.dto.AuthUserInfo;
import com.simpllyrun.srcservice.api.auth.jwt.AuthToken;
import com.simpllyrun.srcservice.api.auth.jwt.AuthTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

/**
 * Success Handler에 진입했다는 것은, 로그인이 완료되었다는 뜻이다.
 * 이 때가 정말 중요하다.
 * <p>
 * 해당 클래스의 주요 기능은 크게 2가지이다.
 * <p>
 * 1. 최초 로그인인지 확인
 * 2. Access Token, Refresh Token 생성 및 발급
 * 3. token을 포함하여 리다이렉트
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final AuthTokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        AuthToken accessToken = tokenProvider.createAuthToken(user.getId().toString(), user.getRoleType().getKey());

        response.addHeader(AuthTokenProvider.HEADER_AUTHORIZATION,
                AuthTokenProvider.TOKEN_PREFIX + accessToken.getToken());
        response.addHeader("accessToken", accessToken.getToken());
        response.sendRedirect("/");
    }
}

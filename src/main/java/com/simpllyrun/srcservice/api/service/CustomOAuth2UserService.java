package com.simpllyrun.srcservice.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpllyrun.srcservice.api.domain.User;
import com.simpllyrun.srcservice.api.dto.auth.OAuthAttributes;
import com.simpllyrun.srcservice.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest); //loadUser 메서드는 서드파티에 사용자 정보를 요청할 수 있는 accessToken을 얻고 나서 실행된다. userRequest에 AccessToken이 있다.
        Map<String, Object> attributes = oAuth2User.getAttributes();

        //OAuth2 서비스 id (구글, 카카오, 네이버)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("registrationId={}", registrationId);

        //OAuth2 로그인 진행 시 키가 되는 필드 값(map의 키값) {google="sub", naver="response", kakao="id"}
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        log.info("userNameAttributeName={}", userNameAttributeName);

        //OAuth2 로그인을 통해 가져온 oAuth2User.getAttributes()를 담아주는 of 메서드
        OAuthAttributes oAuthAttributes = OAuthAttributes.of(registrationId, userNameAttributeName, attributes);

        User user = saveOrUpdate(oAuthAttributes);

        //attribute 찍어보기
        attributes.forEach((k, v)-> {
            log.info(k + ":" + v);
        });

        System.out.println("============================================");

        log.info("전달받은 이메일 주소 = {}",oAuthAttributes.getEmail());
        log.info("전달받은 이름 = {}",oAuthAttributes.getName());

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                oAuthAttributes.getAttributes(),
                oAuthAttributes.getNameAttributeKey());

    }

    private User saveOrUpdate(OAuthAttributes oAuthAttributes) {
        User user = userRepository.findByEmail(oAuthAttributes.getEmail())
                .map(entity -> entity.update(oAuthAttributes.getName(), oAuthAttributes.getEmail()))
                .orElse(oAuthAttributes.toEntity());

        return userRepository.save(user);

    }

}

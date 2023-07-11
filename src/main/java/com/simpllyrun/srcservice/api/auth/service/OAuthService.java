package com.simpllyrun.srcservice.api.auth.service;

import com.simpllyrun.srcservice.api.auth.domain.ProviderType;
import com.simpllyrun.srcservice.api.auth.domain.RoleType;
import com.simpllyrun.srcservice.api.auth.domain.UserPrincipal;
import com.simpllyrun.srcservice.api.user.domain.User;
import com.simpllyrun.srcservice.api.auth.dto.AuthUserInfo;
import com.simpllyrun.srcservice.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class OAuthService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        AuthUserInfo authUserInfo = AuthUserInfo.of(providerType, user.getAttributes());
        User savedUser = userRepository.findByUserOAuthId(authUserInfo.getId())
                .orElse(null);

        if (savedUser != null) {
            if (providerType != savedUser.getProviderType()) {
                log.info("No Match ProviderType : " + providerType + " / " + savedUser.getProviderType());
                throw new InternalAuthenticationServiceException("No Match ProviderType");
            }
            updateUser(savedUser, authUserInfo);
        } else {
            savedUser = createUser(authUserInfo, providerType);
        }
        userRepository.saveAndFlush(savedUser);

        return UserPrincipal.create(savedUser, user.getAttributes());
    }

    private User createUser(AuthUserInfo userInfo, ProviderType providerType) {
        return User.builder()
                .userOAuthId(userInfo.getId())
                .userId(userInfo.getEmail().indexOf("@") > 0 ?
                        userInfo.getEmail().substring(0, userInfo.getEmail().indexOf("@")) : userInfo.getEmail())
                .name(userInfo.getName())
                .email(userInfo.getEmail())
                .profileImageUrl(userInfo.getImageUrl())
                .providerType(providerType)
                .roleType(RoleType.USER)
                .build();
    }

    private void updateUser(User user, AuthUserInfo userInfo) {
        user.update(userInfo.getName(), userInfo.getEmail(), userInfo.getImageUrl());
    }
}

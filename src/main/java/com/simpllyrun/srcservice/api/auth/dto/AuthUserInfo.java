package com.simpllyrun.srcservice.api.auth.dto;

import com.simpllyrun.srcservice.api.auth.domain.ProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserInfo {
    private Map<String, Object> attributes; //OAuth2 반환하는 유저 정보 Map
    private String id;
    private String name;
    private String email;
    private String imageUrl;

    public static AuthUserInfo of(ProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case KAKAO:
                return ofKakao(attributes);
            case NAVER:
                return ofNaver(attributes);
            case GOOGLE:
                return ofGoogle(attributes);
            default:
                throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }

    // 향후 추상화
    private static AuthUserInfo ofKakao(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");// 카카오로 받은 데이터에서 계정 정보가 담긴 kakao_account 값 꺼냄
        Map<String, Object> profile = (Map<String, Object>) attributes.get("profile");

        return AuthUserInfo.builder()
                .id((String) profile.get("id"))
                .name((String) profile.get("nickname"))
                .email((String) profile.get("email"))
                .imageUrl((String) profile.get("thumbnail_image"))
                .attributes(attributes)
                .build();
    }

    private static AuthUserInfo ofNaver(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return AuthUserInfo.builder()
                .id((String) response.get("id"))
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .imageUrl((String) response.get("profile_image"))
                .attributes(response)
                .build();
    }

    private static AuthUserInfo ofGoogle(Map<String, Object> attributes) {
        return AuthUserInfo.builder()
                .id((String) attributes.get("sub"))
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .imageUrl((String) attributes.get("picture"))
                .attributes(attributes)
                .build();
    }
}

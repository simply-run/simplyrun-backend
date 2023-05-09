package com.simpllyrun.srcservice.api.dto.auth;

import com.simpllyrun.srcservice.api.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuthAttributes {
    private Map<String, Object> attributes; //OAuth2 반환하는 유저 정보 Map
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private String registrationId;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
            if (registrationId.equals("kakao")){
                return ofKakao(registrationId, userNameAttributeName, attributes);
            } else if (registrationId.equals("naver")){
                return ofNaver(registrationId, userNameAttributeName, attributes);
            }
            return ofGoogle(registrationId, userNameAttributeName, attributes);
    }
    private static OAuthAttributes ofKakao(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");// 카카오로 받은 데이터에서 계정 정보가 담긴 kakao_account 값 꺼냄
        Map<String, Object> profile = (Map<String, Object>) attributes.get("profile");

        return OAuthAttributes.builder()
                .name((String) profile.get("nickname"))
                .email((String) profile.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .registrationId(registrationId)
                .build();
    }

    private static OAuthAttributes ofNaver(String registrationId, String userNameAttributeName, Map<String, Object> attributes){

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
//                .picture((String) response.get("picture"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .registrationId(registrationId)
                .build();
    }

    private static OAuthAttributes ofGoogle(String registrationId, String userNameAttributeName, Map<String, Object> attributes){


        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .registrationId(registrationId)
                .build();
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .registrationId(registrationId)
                .build();
    }
}

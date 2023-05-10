package com.simpllyrun.srcservice.api.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum ProviderType {
    GOOGLE,
    FACEBOOK,
    NAVER,
    KAKAO,
    LOCAL;
}

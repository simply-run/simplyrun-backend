package com.simpllyrun.srcservice.util;

import com.simpllyrun.srcservice.api.auth.dto.AccessUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {


    public static AccessUser getAuthUser() {
        // dev 환경에서는 무조건 로그인 처리, 로그인 로직 되면 아래 변경
        if (true) {
            return AccessUser.builder()
                    .id(1L)
                    .userId("test")
                    .name("test")
                    .build();
        } else if (SecurityContextHolder.getContext().getAuthentication() != null &&
                (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof AccessUser)) {
            return (AccessUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } else {
            throw new RuntimeException("AccessUser is null");
        }
    }

    public static Long getAuthUserId() {
        try {
            return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (Exception e) {
            throw new RuntimeException("AccessUser is null");
        }
    }
}

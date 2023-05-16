package com.simpllyrun.srcservice.global.util;

import com.simpllyrun.srcservice.api.auth.domain.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    public static Long getAuthUserId() {
        try {
            var userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return Long.parseLong(userPrincipal.getUsername());
        } catch (Exception e) {
            throw new RuntimeException("AccessUser is null");
        }
    }
}

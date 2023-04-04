package com.simpllyrun.srcservice.dto.user;

import com.simpllyrun.srcservice.domain.User;

public interface UserDtoMapper {
    static User toEntity(UserLoginDto userLogin) {
        return User.builder()
                .userId(userLogin.getId())
                .password(userLogin.getPassword())
                .build();
    }
}

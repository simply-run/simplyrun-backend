package com.simpllyrun.srcservice.api.dto.user.mapper;

import com.simpllyrun.srcservice.api.domain.User;
import com.simpllyrun.srcservice.api.dto.user.UserLoginDto;

public interface UserDtoMapper {
    static User toEntity(UserLoginDto userLogin) {
        return User.builder()
                .userId(userLogin.getId())
                .password(userLogin.getPassword())
                .build();
    }
}

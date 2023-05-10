package com.simpllyrun.srcservice.api.dto.user.mapper;

import com.simpllyrun.srcservice.api.domain.User;
import com.simpllyrun.srcservice.api.dto.user.UserDto;

public interface UserDtoMapper {
    static User toEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .build();
    }
}

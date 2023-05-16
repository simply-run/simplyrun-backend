package com.simpllyrun.srcservice.api.user.dto.mapper;

import com.simpllyrun.srcservice.api.user.domain.User;
import com.simpllyrun.srcservice.api.user.dto.UserDto;

public interface UserDtoMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}

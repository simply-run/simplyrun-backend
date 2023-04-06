package com.simpllyrun.srcservice.api.dto.user;

import com.simpllyrun.srcservice.api.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "사용자 정보 DTO")
public class UserDto {

    @Schema(description = "아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;
    @Schema(description = "이름", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    public static UserDto of(User user) {
        return UserDto.builder()
                .id(user.getUserId())
                .name(user.getName())
                .build();
    }
}

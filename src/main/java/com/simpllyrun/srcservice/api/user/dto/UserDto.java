package com.simpllyrun.srcservice.api.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.simpllyrun.srcservice.api.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "사용자 정보 DTO")
public class UserDto {

    @NotNull
    @Schema(description = "아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @NotNull
    @Schema(description = "사용자 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;
    @NotNull
    @Schema(description = "이름", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    public static UserDto of(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .name(user.getName())
                .build();
    }

    @Builder
    public UserDto(Long id, String userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
    }

    @QueryProjection
    public UserDto(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.name = user.getName();
    }
}

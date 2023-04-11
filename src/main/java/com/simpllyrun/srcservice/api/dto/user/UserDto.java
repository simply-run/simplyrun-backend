package com.simpllyrun.srcservice.api.dto.user;

import com.simpllyrun.srcservice.api.domain.User;
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
    private String id;
    @NotNull
    @Schema(description = "이름", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    public static UserDto of(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getUserId())
                .name(user.getName())
                .build();
    }
}

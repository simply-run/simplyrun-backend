package com.simpllyrun.srcservice.api.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "로그인 DTO")
public class UserLoginDto {

    @Schema(description = "아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;
    @Schema(description = "패스워드", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}

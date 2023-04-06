package com.simpllyrun.srcservice.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "인증 DTO")
public class AuthDto {
    @Schema(description = "아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;
    @Schema(description = "패스워드", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}

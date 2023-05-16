package com.simpllyrun.srcservice.api.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "인증 DTO")
public class AuthDto {
    @NotNull
    @Schema(description = "아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;
    @NotNull
    @Schema(description = "패스워드", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}

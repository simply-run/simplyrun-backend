package com.simpllyrun.srcservice.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "인증 DTO")
public class AccessUser {

    @Schema(description = "아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    @Schema(description = "사용자 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;
    @Schema(description = "이름", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
}

package com.simpllyrun.srcservice.api.dto.follow;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "사용자 정보 DTO")
public class FollowDto {

    @NotNull
//    @Schema(description = "아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    @NotNull
//    @Schema(description = "이름", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer followCount;
}

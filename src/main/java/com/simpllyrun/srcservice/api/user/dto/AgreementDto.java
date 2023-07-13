package com.simpllyrun.srcservice.api.user.dto;

import com.simpllyrun.srcservice.api.user.domain.AgreementType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "서비스 이용약관 DTO")
public class AgreementDto {

    @Schema(description = "약관 유형")
    private AgreementType type;
    @Schema(description = "약관 이름")
    private String text;
    @Schema(description = "약관 필수 여부")
    private boolean isRequired;
}

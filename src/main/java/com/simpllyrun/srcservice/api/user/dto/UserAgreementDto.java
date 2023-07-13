package com.simpllyrun.srcservice.api.user.dto;

import com.simpllyrun.srcservice.api.user.domain.AgreementType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "사용자 이용약관 DTO")
public class UserAgreementDto {

    @Schema(description = "약관 유형", requiredMode = Schema.RequiredMode.REQUIRED)
    private AgreementType agreementType;
    @Schema(description = "약관 동의 여부", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isAgree;
}

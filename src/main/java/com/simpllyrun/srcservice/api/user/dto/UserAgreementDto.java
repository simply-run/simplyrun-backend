package com.simpllyrun.srcservice.api.user.dto;

import com.simpllyrun.srcservice.api.user.domain.AgreementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAgreementDto {

    private AgreementType agreementType;
    private Boolean isAgree;
}

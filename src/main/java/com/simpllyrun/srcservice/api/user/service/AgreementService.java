package com.simpllyrun.srcservice.api.user.service;

import com.simpllyrun.srcservice.api.user.dto.AgreementDto;
import com.simpllyrun.srcservice.api.user.dto.UserAgreementDto;

import java.util.List;

public interface AgreementService {

    Long addUserAgreement(List<UserAgreementDto> userAgreementDto);
    void updateUserAgreement(List<UserAgreementDto> userAgreementDto);
    List<UserAgreementDto> getUserAgreements();
    List<AgreementDto> getAgreements();
}

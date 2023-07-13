package com.simpllyrun.srcservice.api.user.service;

import com.simpllyrun.srcservice.api.user.dto.UserAgreementDto;

import java.util.List;

public interface UserAgreementService {

    Long addAgreement(List<UserAgreementDto> userAgreementDto);
    void updateAgreement(List<UserAgreementDto> userAgreementDto);
    List<UserAgreementDto> getUserAgreements();
}

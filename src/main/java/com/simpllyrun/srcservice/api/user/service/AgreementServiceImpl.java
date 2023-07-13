package com.simpllyrun.srcservice.api.user.service;

import com.simpllyrun.srcservice.api.user.domain.User;
import com.simpllyrun.srcservice.api.user.domain.UserAgreement;
import com.simpllyrun.srcservice.api.user.dto.AgreementDto;
import com.simpllyrun.srcservice.api.user.dto.UserAgreementDto;
import com.simpllyrun.srcservice.api.user.dto.mapper.AgreementDtoMapper;
import com.simpllyrun.srcservice.api.user.dto.mapper.UserAgreementDtoMapper;
import com.simpllyrun.srcservice.api.user.repository.AgreementRepository;
import com.simpllyrun.srcservice.api.user.repository.UserAgreementRepository;
import com.simpllyrun.srcservice.api.user.repository.UserRepository;
import com.simpllyrun.srcservice.global.error.ErrorCode;
import com.simpllyrun.srcservice.global.error.SrcException;
import com.simpllyrun.srcservice.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgreementServiceImpl implements AgreementService {

    private final UserAgreementRepository userAgreementRepository;
    private final AgreementRepository agreementRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long addUserAgreement(List<UserAgreementDto> userAgreementDtos) {
        User user = getUser();

        List<UserAgreement> userAgreements = new ArrayList<>();
        userAgreementDtos.forEach(dto -> {
            var item = UserAgreementDtoMapper.INSTANCE.toEntity(dto);
            item.setUser(user);
        });

        var saves = userAgreementRepository.saveAll(userAgreements);
        return (long) saves.size();
    }

    @Override
    @Transactional
    public void updateUserAgreement(List<UserAgreementDto> userAgreementDto) {
        var userAgreements = userAgreementRepository.findAllByUserId(AuthUtil.getAuthUserId());
        userAgreements.forEach(dto -> userAgreementDto.stream()
                .filter(item -> item.getAgreementType().equals(dto.getAgreement().getType()))
                .findFirst().ifPresent(item -> dto.setAgreed(item.getIsAgree())));

        userAgreementRepository.saveAll(userAgreements);
    }

    @Override
    public List<UserAgreementDto> getUserAgreements() {
        Long userId = AuthUtil.getAuthUserId();
        return UserAgreementDtoMapper.INSTANCE.toDtos(userAgreementRepository.findAllByUserId(userId));
    }

    @Override
    public List<AgreementDto> getAgreements() {
        return AgreementDtoMapper.INSTANCE.toDtos(agreementRepository.findAll());
    }

    private User getUser() {
        return userRepository.findById(AuthUtil.getAuthUserId())
                .orElseThrow(() -> new SrcException(ErrorCode.USER_NOT_FOUND));
    }
}
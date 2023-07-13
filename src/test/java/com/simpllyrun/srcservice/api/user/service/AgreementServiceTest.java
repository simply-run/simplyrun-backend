package com.simpllyrun.srcservice.api.user.service;

import com.simpllyrun.srcservice.api.user.domain.Agreement;
import com.simpllyrun.srcservice.api.user.domain.AgreementType;
import com.simpllyrun.srcservice.api.user.domain.User;
import com.simpllyrun.srcservice.api.user.domain.UserAgreement;
import com.simpllyrun.srcservice.api.user.dto.UserAgreementDto;
import com.simpllyrun.srcservice.api.user.dto.mapper.UserAgreementDtoMapper;
import com.simpllyrun.srcservice.api.user.repository.AgreementRepository;
import com.simpllyrun.srcservice.api.user.repository.UserAgreementRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class AgreementServiceTest {

    @MockBean
    private UserAgreementRepository userAgreementRepository;

    @MockBean
    private AgreementRepository agreementRepository;

    @Autowired
    private AgreementService agreementService;

    @Test
    @DisplayName("사용자 동의 목록 조회")
    @WithMockUser(username = "1")
    void getUserAgreementTest() {
        // given
        var userAgreement = UserAgreement.builder()
                .agreement(Agreement.builder()
                        .type(AgreementType.TERMS_OF_SERVICE)
                        .build())
                .user(User.builder()
                        .build())
                .isAgree(true)
                .build();
        given(userAgreementRepository.findAllByUserId(1L))
                .willReturn(List.of(userAgreement));

        // when
        var userAgreements = agreementService.getUserAgreements();

        // then
        assertThat(userAgreements.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("사용자 동의 목록 추가")
    @WithMockUser(username = "1")
    void addUserAgreementTest() {
        // given
        var userAgreementDto = UserAgreementDto.builder()
                .agreementType(AgreementType.TERMS_OF_SERVICE)
                .isAgree(true)
                .build();

        var userAgreement = UserAgreementDtoMapper.INSTANCE.toEntity(userAgreementDto);
        userAgreement.setUser(User.builder()
                .build());
        given(userAgreementRepository.saveAll(anyList()))
                .willReturn(List.of(userAgreement));

        // when
        var userAgreementId = agreementService.addUserAgreement(List.of(userAgreementDto));


        // then
        assertThat(userAgreementId).isEqualTo(1L);
    }

    @Test
    @DisplayName("사용자 동의 목록 수정")
    @WithMockUser(username = "1")
    void updateUserAgreementTest() {
        // given
        var userAgreementDto = UserAgreementDto.builder()
                .agreementType(AgreementType.TERMS_OF_SERVICE)
                .isAgree(true)
                .build();

        var userAgreement = UserAgreementDtoMapper.INSTANCE.toEntity(userAgreementDto);
        userAgreement.setUser(User.builder()
                .build());
        given(userAgreementRepository.saveAll(anyList()))
                .willReturn(List.of(userAgreement));

        // when
        agreementService.updateUserAgreement(List.of(userAgreementDto));

        // then
        verify(userAgreementRepository).saveAll(anyList());
    }

    @Test
    @DisplayName("약관 목록 조회")
    void getAgreementsTest() {
        // given
        var agreement = Agreement.builder()
                .type(AgreementType.TERMS_OF_SERVICE)
                .build();
        given(agreementRepository.findAll())
                .willReturn(List.of(agreement));

        // when
        var agreements = agreementService.getAgreements();

        // then
        assertThat(agreements.size()).isEqualTo(1);
    }
}

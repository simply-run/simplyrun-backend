package com.simpllyrun.srcservice.global.config;

import com.simpllyrun.srcservice.api.user.domain.Agreement;
import com.simpllyrun.srcservice.api.user.domain.AgreementType;
import com.simpllyrun.srcservice.api.user.domain.User;
import com.simpllyrun.srcservice.api.user.repository.AgreementRepository;
import com.simpllyrun.srcservice.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("local")
/**
 * 테스트 환경에서만 동작하는 Profile 설정 필요
 */
public class RunConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AgreementRepository agreementRepository;

    @Override
    public void run(final String... args) {
        userRepository.save(User.builder().userId("user").name("test").build());

        agreementRepository.saveAll(getAgreements());
    }

    private List<Agreement> getAgreements() {
        return List.of(Agreement.builder()
                        .type(AgreementType.TERMS_OF_SERVICE)
                        .required(true)
                        .text(AgreementType.TERMS_OF_SERVICE.getDisplayName())
                        .build(),
                Agreement.builder()
                        .type(AgreementType.PRIVACY_POLICY)
                        .required(true)
                        .text(AgreementType.PRIVACY_POLICY.getDisplayName())
                        .build(),
                Agreement.builder()
                        .type(AgreementType.LOCATION_SERVICE)
                        .required(true)
                        .text(AgreementType.LOCATION_SERVICE.getDisplayName())
                        .build(),
                Agreement.builder()
                        .type(AgreementType.OPTIONAL_AGREEMENT)
                        .text(AgreementType.OPTIONAL_AGREEMENT.getDisplayName())
                        .build(),
                Agreement.builder()
                        .type(AgreementType.EVENT_ADVERTISEMENT)
                        .text(AgreementType.EVENT_ADVERTISEMENT.getDisplayName())
                        .build());
    }
}

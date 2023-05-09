package com.simpllyrun.srcservice.config;

import com.simpllyrun.srcservice.api.domain.User;
import com.simpllyrun.srcservice.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
/**
 * 테스트 환경에서만 동작하는 Profile 설정 필요
 */
public class RunConfig implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(final String... args) {
        userRepository.save(User.builder().userId("user").name("test").build());
    }
}

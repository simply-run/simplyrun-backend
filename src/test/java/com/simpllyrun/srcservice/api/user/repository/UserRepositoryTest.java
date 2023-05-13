package com.simpllyrun.srcservice.api.user.repository;

import com.simpllyrun.srcservice.api.user.repository.UserRepository;
import com.simpllyrun.srcservice.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(TestConfig.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUserIdTest() {
    }
}
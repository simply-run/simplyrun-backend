package com.simpllyrun.srcservice.api.service;

import com.simpllyrun.srcservice.api.repository.UserRepository;
import com.simpllyrun.srcservice.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
//@Import(TestConfig.class)
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void getUserTest() {
    }
}
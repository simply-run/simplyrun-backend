package com.simpllyrun.srcservice.api.user.service;

import com.simpllyrun.srcservice.api.user.repository.UserRepository;
import com.simpllyrun.srcservice.api.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
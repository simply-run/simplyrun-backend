package com.simpllyrun.srcservice.api.user.service;

import com.simpllyrun.srcservice.api.user.domain.User;
import com.simpllyrun.srcservice.api.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUser() {
        // TODO Spring Security 적용 후 수정
        String userId = "test";

        return userRepository.findByUserId(userId).orElse(null);
    }
}

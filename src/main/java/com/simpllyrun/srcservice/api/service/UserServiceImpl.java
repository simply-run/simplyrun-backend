package com.simpllyrun.srcservice.api.service;

import com.simpllyrun.srcservice.api.domain.User;
import com.simpllyrun.srcservice.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUser() {
        // TODO Spring Security 적용 후 수정
        String userId = "test";

        return userRepository.findByUserId(userId);
    }
}

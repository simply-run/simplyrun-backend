package com.simpllyrun.srcservice.api.user.service;

import com.simpllyrun.srcservice.api.user.domain.User;
import com.simpllyrun.srcservice.api.user.repository.UserRepository;
import com.simpllyrun.srcservice.global.util.AuthUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUser() {
        Long userId = AuthUtil.getAuthUserId();

        return userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
    }
}

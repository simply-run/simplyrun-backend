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
    @Transactional(readOnly = true)
    public Boolean userLogin(User user) {
        var foundUser = userRepository.findByUserId(user.getUserId());
        // token 인증 시 토큰 비교
        if (foundUser == null || !encEqualPassword(foundUser.getPassword(), user.getPassword())) {
            return false;
        }

        // jwt 토큰 return 후 filter 이용해, 요청마다 token 비교 후 유효한 인증만 허용
        return true;
    }

    private boolean encEqualPassword(String dbPassword, String password) {
        // 암호화 로직으로 password 파라미터 암호화 해서 비교
        if (dbPassword.equals(password)) {
            return true;
        }
        return false;
    }
}

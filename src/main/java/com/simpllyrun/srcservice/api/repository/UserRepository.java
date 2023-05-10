package com.simpllyrun.srcservice.api.repository;

import com.simpllyrun.srcservice.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

    //네이버 테스트
    Optional<User> findByEmail(String email);
}

package com.simpllyrun.srcservice.api.repository;

import com.simpllyrun.srcservice.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserId(String userId);
}

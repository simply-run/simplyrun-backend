package com.simpllyrun.srcservice.api.repository;

import com.simpllyrun.srcservice.api.domain.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {

    List<UserFollow> findByToUserId(Long userId);
}

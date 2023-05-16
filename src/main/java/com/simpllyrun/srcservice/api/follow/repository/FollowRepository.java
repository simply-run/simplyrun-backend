package com.simpllyrun.srcservice.api.follow.repository;

import com.simpllyrun.srcservice.api.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryCustom {

    List<Follow> findByUserId(Long userId);
}

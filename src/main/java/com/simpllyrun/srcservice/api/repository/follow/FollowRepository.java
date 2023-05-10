package com.simpllyrun.srcservice.api.repository.follow;

import com.simpllyrun.srcservice.api.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryCustom {

    List<Follow> findByUserId(Long userId);
}

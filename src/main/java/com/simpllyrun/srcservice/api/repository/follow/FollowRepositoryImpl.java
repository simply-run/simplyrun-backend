package com.simpllyrun.srcservice.api.repository.follow;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simpllyrun.srcservice.api.domain.QFollow;
import com.simpllyrun.srcservice.api.dto.follow.FollowerDto;
import com.simpllyrun.srcservice.api.dto.follow.QFollowerDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.simpllyrun.srcservice.api.domain.QUser.user;
import static com.simpllyrun.srcservice.api.domain.QFollow.follow;

@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<FollowerDto> findFollowings(Long loginId, Long userId) {
        return findFollowerDtoInMemberIdList(loginId, findFollowingIdList(userId));
    }

    @Override
    public List<FollowerDto> findFollowers(Long loginId, Long userId) {
        return findFollowerDtoInMemberIdList(loginId, findFollowerIdList(userId));
    }


    private List<FollowerDto> findFollowerDtoInMemberIdList(Long loginId, JPQLQuery<Long> userIds) {
        return queryFactory
                .select(new QFollowerDto(
                        user,
                        JPAExpressions
                                .selectFrom(follow)
                                .where(follow.user.id.eq(loginId).and(follow.followUser.eq(user)))
                                .exists(),
                        JPAExpressions
                                .selectFrom(follow)
                                .where(follow.user.eq(user).and(follow.followUser.id.eq(loginId)))
                                .exists(),
                        user.id.eq(loginId)
                ))
                .from(user)
                .where(user.id.in(userIds))
                .fetch();
    }

    private JPQLQuery<Long> findFollowingIdList(Long userId) {
        return JPAExpressions
                .select(follow.followUser.id)
                .from(follow)
                .where(follow.user.id.eq(userId));
    }

    private JPQLQuery<Long> findFollowerIdList(Long userId) {
        return JPAExpressions
                .select(follow.user.id)
                .from(follow)
                .where(follow.followUser.id.eq(userId));
    }
}

package com.simpllyrun.srcservice.api.feed.repository.post;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simpllyrun.srcservice.api.feed.domain.Post;
import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import com.simpllyrun.srcservice.api.feed.dto.QPostDto_PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.simpllyrun.srcservice.api.feed.domain.QComment.comment;
import static com.simpllyrun.srcservice.api.feed.domain.QPost.post;
import static com.simpllyrun.srcservice.api.follow.domain.QFollow.follow;
import static com.simpllyrun.srcservice.api.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional
    public Post findByIdFetchJoin(Long postId) {

        Post findPost = jpaQueryFactory.selectFrom(post)
                .where(post.id.eq(postId))
                .leftJoin(post.user).fetchJoin()
                .leftJoin(post.postImages).fetchJoin()
//                .leftJoin(post.postLikes).fetchJoin()
//                .leftJoin(post.comments).fetchJoin()
                .fetchOne();

        return findPost;
    }

    @Override
    public Page<PostDto.PostResponseDto> findPostDtoOfFollowingByUserIdentity(Long userIdentity , Pageable pageable) {
        var postDtos = jpaQueryFactory.select(new QPostDto_PostResponseDto(
                        post.id,
                        post.user,
                        post.title,
                        post.content,
                        post.categoryType,
                        post.dateCreated,
                        post.dateUpdated,
                        post.comments.size(),
                        post.postLikes.size()
                )).from(post)
                .innerJoin(post.user, user)
                .where(post.user.id.in(getFollowingUserIdsByUserId(userIdentity)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.id.desc())
                .fetch();

        return new PageImpl<>(postDtos, pageable, postDtos.size());
    }

    private JPQLQuery<Long> getFollowingUserIdsByUserId(Long userIdentity) {
        return JPAExpressions
                .select(follow.followUser.id)
                .from(follow)
                .where(follow.user.id.eq(userIdentity));
    }
}

package com.simpllyrun.srcservice.api.feed.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simpllyrun.srcservice.api.feed.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.simpllyrun.srcservice.api.feed.domain.QPost.*;

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

}

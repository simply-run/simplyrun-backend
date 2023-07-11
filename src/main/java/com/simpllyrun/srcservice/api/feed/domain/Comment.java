package com.simpllyrun.srcservice.api.feed.domain;

import com.simpllyrun.srcservice.api.base.domain.BaseDomain;
import com.simpllyrun.srcservice.api.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
public class Comment extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;



    @Builder
    public Comment(Long id, User user, Post post, String content) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.content = content;
    }

    public void updateContent(String content){
        this.content = content;
    }
}

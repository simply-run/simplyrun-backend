package com.simpllyrun.srcservice.api.domain.feed;

import com.simpllyrun.srcservice.api.domain.BaseDomain;
import com.simpllyrun.srcservice.api.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    private String content;


    @Builder
    public Comment(User user, Post post, String content) {
        this.user = user;
        this.post = post;
        this.content = content;
    }
}

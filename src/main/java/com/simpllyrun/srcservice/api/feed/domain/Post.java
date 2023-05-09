package com.simpllyrun.srcservice.api.feed.domain;

import com.simpllyrun.srcservice.api.domain.BaseDomain;
import com.simpllyrun.srcservice.api.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "post")
public class Post extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private CategoryEnum category;
    private String content;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikes = new ArrayList<>();


    public enum CategoryEnum {
        COMMUNITY, MARKET, MARATHON, CREW, QNA
    }


    @Builder
    public Post(User user, CategoryEnum category, String content) {
        this.user = user;
        this.category = category;
        this.content = content;
    }
}

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

    @Enumerated(EnumType.STRING)
    private CategoryEnum categoryType;
    private String content;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikes = new ArrayList<>();


    public enum CategoryEnum {
        COMMUNITY, MARKET, MARATHON, CREW, QNA
    }


    @Builder
    public Post(Long id, User user, CategoryEnum categoryType, String content) {
        this.id = id;
        this.user = user;
        this.categoryType = categoryType;
        this.content = content;
    }

    public void update(String content){
        this.content = content;
    }
}

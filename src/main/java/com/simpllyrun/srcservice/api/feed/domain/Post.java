package com.simpllyrun.srcservice.api.feed.domain;

import com.simpllyrun.srcservice.api.base.domain.BaseDomain;
import com.simpllyrun.srcservice.api.user.domain.User;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private CategoryEnum categoryType;

    private String title;
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PostLike> postLikes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<PostImage> postImages = new ArrayList<>();

    public enum CategoryEnum {
        COMMUNITY, MARKET, MARATHON, CREW, QNA, RECOMMEND
    }


    @Builder
    public Post(Long id, User user, CategoryEnum categoryType, String title, String content) {
        this.id = id;
        this.user = user;
        this.categoryType = categoryType;
        this.title = title;
        this.content = content;
    }

    public void updateText(String title, String content){
        this.title = title;
        this.content = content;
    }
    public void updateImage(List<PostImage> postImages){this.postImages = postImages;}
    public void updateComments(List<Comment> comments){this.comments = comments;}
    public void updatePostLikes(List<PostLike> postLikes){this.postLikes = postLikes;}
    public void setComments(List<Comment> comments) {
        this.comments.addAll(comments);
    }
}

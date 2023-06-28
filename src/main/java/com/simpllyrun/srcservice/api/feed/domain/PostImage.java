package com.simpllyrun.srcservice.api.feed.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post_image")
public class PostImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalFilename;
    private String storeFilename;
    private String postImageUrl;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public PostImage(String originalFilename, String storeFilename, String postImageUrl) {
        this.originalFilename = originalFilename;
        this.storeFilename = storeFilename;
        this.postImageUrl = postImageUrl;
    }

    public void setPost(Post post){
        this.post = post;
    }
}

package com.simpllyrun.srcservice.api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "follow")
@DynamicInsert
@DynamicUpdate
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 팔로우 받는 사람
    @ManyToOne(fetch = FetchType.LAZY)
    private User followUser; // 헷갈려서 작성 user를 팔로우 하고 있는 사람
    @CreationTimestamp
    private LocalDateTime dateCreated;
    @UpdateTimestamp
    private LocalDateTime dateUpdated;

    @Builder
    public Follow(Long id, User user, User followUser) {
        this.id = id;
        this.user = user;
        this.followUser = followUser;
    }
}

package com.simpllyrun.srcservice.api.follow.domain;

import com.simpllyrun.srcservice.api.base.domain.BaseDomain;
import com.simpllyrun.srcservice.api.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "follow")
public class Follow extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_user_id")
    private User followUser;

    @Builder
    public Follow(Long id, User user, User followUser) {
        this.id = id;
        this.user = user;
        this.followUser = followUser;
    }
}

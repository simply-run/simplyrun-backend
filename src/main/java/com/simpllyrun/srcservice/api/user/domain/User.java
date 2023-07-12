package com.simpllyrun.srcservice.api.user.domain;

import com.simpllyrun.srcservice.api.auth.domain.ProviderType;
import com.simpllyrun.srcservice.api.auth.domain.RoleType;
import com.simpllyrun.srcservice.api.base.domain.BaseDomain;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userOAuthId;
    @Column(unique = true, length = 50)
    private String userId;
    private String name;
    private String email;
    @Column(length = 512)
    private String profileImageUrl;
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public void update(String name, String email, String imageUrl) {
        if (name != null && !name.equals(this.name)) {
            this.name = name;
        }
        if (imageUrl != null && !imageUrl.equals(this.profileImageUrl)) {
            this.profileImageUrl = imageUrl;
        }
        if (email != null && !email.equals(this.email)) {
            this.email = email;
        }
    }

    public void changeRole(RoleType role) {
        this.roleType = role;
    }

    @Builder
    public User(Long id, String userOAuthId, String userId, String name, String email, String profileImageUrl, ProviderType providerType, RoleType roleType) {
        this.id = id;
        this.userOAuthId = userOAuthId;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.providerType = providerType;
        this.roleType = roleType;
    }
}

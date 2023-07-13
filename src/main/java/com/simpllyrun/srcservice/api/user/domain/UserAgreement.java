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
@Table(name = "user_agreement")
public class UserAgreement extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agreement_id")
    private Agreement agreement;

    private Boolean isAgree;

    public void setUser(User user) {
        this.user = user;
    }

    public void setAgreed(Boolean isAgree) {
        this.isAgree = isAgree;
    }

    @Builder
    public UserAgreement(Long id, User user, Agreement agreement, Boolean isAgree) {
        this.id = id;
        this.user = user;
        this.agreement = agreement;
        this.isAgree = isAgree;
    }
}

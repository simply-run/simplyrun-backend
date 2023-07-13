package com.simpllyrun.srcservice.api.user.domain;

import com.simpllyrun.srcservice.api.base.domain.BaseDomain;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "agreement")
public class Agreement extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AgreementType type;
    private String text;

    @Builder
    public Agreement(Long id, AgreementType type, String text) {
        this.id = id;
        this.type = type;
        this.text = text;
    }
}

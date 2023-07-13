package com.simpllyrun.srcservice.api.user.domain;

import com.simpllyrun.srcservice.api.base.domain.BaseDomain;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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
    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean required;

    @Builder
    public Agreement(Long id, AgreementType type, String text, boolean required) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.required = required;
    }
}

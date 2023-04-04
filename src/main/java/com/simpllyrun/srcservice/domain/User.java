package com.croquiscom.api.domain;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@DynamicInsert
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String userId;
    private String password;
    @ColumnDefault("15")
    private Float leftCnt;
    @CreationTimestamp
    private LocalDate dateCreated;
    @UpdateTimestamp
    private LocalDate dateUpdated;

    public void setLeftCnt(Float leftCnt) {
        this.leftCnt = leftCnt;
    }

    @Builder
    public User(Integer id, String userId, String password) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.leftCnt = 15f;
    }
}

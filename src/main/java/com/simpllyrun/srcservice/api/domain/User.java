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
    private String name;
    @CreationTimestamp
    private LocalDate dateCreated;
    @UpdateTimestamp
    private LocalDate dateUpdated;

    //role
    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;

    @Builder
    public User(Integer id, String userId, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public User update(String name, String email){
        this.name=name;
        this.email=email;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}

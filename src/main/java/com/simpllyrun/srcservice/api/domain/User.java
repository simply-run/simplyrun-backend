package com.simpllyrun.srcservice.api.domain;

import jakarta.persistence.*;
import lombok.*;
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
    private Role role = Role.USER;

    private String email;

    @Column(name = "resource_server")
    private String registrationId;

    @Builder
    public User(Integer id, String userId, String name, String email, String password, String registrationId) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.registrationId = registrationId; //db에 Resource Server 구분
    }

    public User update(String name, String email){
        this.name=name;
        this.email=email;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    @Getter
    @RequiredArgsConstructor
    public enum Role {
        ADMIN("ROLE_GUEST", "게스트"),
        USER("ROLE_USER", "일반 사용자");

        private final String key;
        private final String title;
    }

    public void changeRole(Role role){
        this.role = role;
    }
}

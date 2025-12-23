package com.dhiraj.miniurl.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class UserEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false, unique = true)
    private String email;

    @Getter
    @Column(nullable = false)
    private String password;

    @Getter
    @Column(nullable = false)
    private String plan;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected UserEntity() {}

    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
        this.plan = "FREE";
        this.createdAt = LocalDateTime.now();
    }
}

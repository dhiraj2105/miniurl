package com.dhiraj.miniurl.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "users")
public class UserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false)
    private String password;


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

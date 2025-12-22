package com.dhiraj.miniurl.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(
        name = "urls",
        indexes = {
                @Index(name = "idx_short_code", columnList = "shortCode", unique = true)
        }
)
public class UrlEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, length = 10, unique = true)
        private String shortCode;

        @Column(nullable = false, columnDefinition = "TEXT")
        private String originalUrl;

        @Column(nullable = false)
        private boolean anonymous;

        @Column(nullable = false)
        private LocalDateTime createdAt;

        protected UrlEntity() {}

        public UrlEntity(String shortCode, String originalUrl, boolean anonymous) {
                this.shortCode = shortCode;
                this.originalUrl = originalUrl;
                this.anonymous = anonymous;
                this.createdAt = LocalDateTime.now();
        }

}

package com.dhiraj.miniurl.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(
        name = "urls",
        indexes = {
                @Index(name = "idx_short_code", columnList = "shortCode", unique = true),
                @Index(name = "idx_user_id", columnList = "user_id")
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

        /**
         * NULL  -> anonymous URL
         * NOT NULL -> registered user URL
         */
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private UserEntity user;

        @Column(nullable = false)
        private Long clickCount;

        @Column(nullable = false)
        private LocalDateTime createdAt;

        protected UrlEntity() {}

        // Anonymous URL
        public UrlEntity(String shortCode, String originalUrl) {
                this.shortCode = shortCode;
                this.originalUrl = originalUrl;
                this.user = null;
                this.createdAt = LocalDateTime.now();
                this.clickCount = 0L;
        }

        // Registered user URL
        public UrlEntity(String shortCode, String originalUrl, UserEntity user) {
                this.shortCode = shortCode;
                this.originalUrl = originalUrl;
                this.user = user;
                this.createdAt = LocalDateTime.now();
                this.clickCount = 0L;
        }

        public void incrementClicks(long value) {
                this.clickCount += value;
        }
}

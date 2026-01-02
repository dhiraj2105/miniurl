package com.dhiraj.miniurl.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey SECRET;
    private final long EXPIRATION;

    public JwtUtil(
            @Value("${app.jwt.secret}") String SECRET,
            @Value("${app.jwt.expiration}") long EXPIRATION
    ) {
        this.SECRET = Keys.hmacShaKeyFor(SECRET.getBytes());
        this.EXPIRATION = EXPIRATION;
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SECRET,SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

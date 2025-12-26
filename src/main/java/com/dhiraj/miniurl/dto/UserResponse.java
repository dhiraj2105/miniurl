package com.dhiraj.miniurl.dto;

import java.time.LocalDateTime;

public record UserResponse (
    Long id,
    String email,
    String plan,
    LocalDateTime createdAt
)
{}
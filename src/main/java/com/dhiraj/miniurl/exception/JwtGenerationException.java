package com.dhiraj.miniurl.exception;

public class JwtGenerationException extends AuthException {
    public JwtGenerationException() {
        super("Failed to generate authentication token");
    }
}

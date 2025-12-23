package com.dhiraj.miniurl.exception;

public class InvalidCredentialsException extends AuthException{
    public InvalidCredentialsException() {
        super("Invalid credentials: " );
    }
}

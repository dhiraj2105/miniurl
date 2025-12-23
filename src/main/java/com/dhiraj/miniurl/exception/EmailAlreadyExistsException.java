package com.dhiraj.miniurl.exception;

public class EmailAlreadyExistsException extends AuthException{
    public EmailAlreadyExistsException(String message) {
        super("Email already exists: " + message);
    }
}

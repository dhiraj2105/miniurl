package com.dhiraj.miniurl.exception;

public class AnonymousLimitReachedException extends RuntimeException{
    public AnonymousLimitReachedException(){
        super("Anonymous users can create only one short URL");
    }
}

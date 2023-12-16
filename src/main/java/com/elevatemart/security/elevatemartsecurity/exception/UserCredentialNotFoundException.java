package com.elevatemart.security.elevatemartsecurity.exception;

public class UserCredentialNotFoundException extends RuntimeException{
    public UserCredentialNotFoundException(){

    }
    public UserCredentialNotFoundException(String message){
        super(message);
    }
}

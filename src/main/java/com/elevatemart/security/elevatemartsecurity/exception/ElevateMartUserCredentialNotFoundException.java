package com.elevatemart.security.elevatemartsecurity.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ElevateMartUserCredentialNotFoundException extends RuntimeException{
    public ElevateMartUserCredentialNotFoundException(String message){
        super(message);
    }
}

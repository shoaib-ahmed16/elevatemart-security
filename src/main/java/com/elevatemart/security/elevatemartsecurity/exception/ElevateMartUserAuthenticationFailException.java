package com.elevatemart.security.elevatemartsecurity.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ElevateMartUserAuthenticationFailException extends RuntimeException{

    public ElevateMartUserAuthenticationFailException(String message){
        super(message);
    }
}

package com.elevatemart.security.elevatemartsecurity.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ElevateMartUserException extends  RuntimeException{
    public ElevateMartUserException(String message){
        super(message);
    }
}

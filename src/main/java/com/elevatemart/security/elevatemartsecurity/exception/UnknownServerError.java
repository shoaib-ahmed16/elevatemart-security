package com.elevatemart.security.elevatemartsecurity.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnknownServerError extends  RuntimeException{
    public  UnknownServerError(String message){
        super(message);
    }
}

package com.elevatemart.security.elevatemartsecurity.exception;

public class UnknownServerError extends  RuntimeException{
    public UnknownServerError(){

    }
    public  UnknownServerError(String message){
        super(message);
    }
}

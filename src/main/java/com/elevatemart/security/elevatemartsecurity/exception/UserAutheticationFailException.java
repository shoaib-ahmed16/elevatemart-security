package com.elevatemart.security.elevatemartsecurity.exception;

public class UserAutheticationFailException extends RuntimeException{
    public  UserAutheticationFailException(){

    }
    public UserAutheticationFailException(String message){
        super(message);
    }
}

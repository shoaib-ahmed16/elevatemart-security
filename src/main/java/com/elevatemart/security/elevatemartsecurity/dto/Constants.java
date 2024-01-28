package com.elevatemart.security.elevatemartsecurity.dto;

public enum Constants {

    AUTHORIZATION("Authorization"),
    TOKEN_PREFIX("Bearer "),
    USERNAME("username"),
    AUTHORITIES("authorities"),
    PASSWORD("password");
    private final String value;
    public String getValue(){
        return this.value;
    }
    Constants(String value){
        this.value =value;
    }
}

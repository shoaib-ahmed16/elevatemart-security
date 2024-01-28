package com.elevatemart.security.elevatemartsecurity.dto;

public enum Constants {

    AUTHORIZATION("Authorization"),
    TOKEN_PREFIX("Bearer "),
    JWT_SUBJECT("JWT TOKEN"),
    JWT_ISSUER("Elevate Mart Security"),

    USERNAME("username"),
    AUTHORITIES("authorities");
    private final String value;
    public String getValue(){
        return this.value;
    }
    Constants(String value){
        this.value =value;
    }
}

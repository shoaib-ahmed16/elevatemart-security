package com.elevatemart.security.elevatemartsecurity.domain;

public enum ROLE {

    PREFIX("ROLE_",20),
    ADMIN("ADMIN",1),
    USER("USER",2);

    private final String name;
    private final int number;
    public String getName(){
       return this.name;
    }
    public int getNumber(){
        return this.number;
    }


    ROLE(String name, int number) {
        this.name=name;
        this.number=number;
    }
}

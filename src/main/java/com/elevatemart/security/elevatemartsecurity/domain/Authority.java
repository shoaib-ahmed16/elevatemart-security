package com.elevatemart.security.elevatemartsecurity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer authId;
    private  String name;

    public void setName(String name){
        this.name= name.toUpperCase();
    }

    @JsonIgnore
    @ManyToOne
    private ElevateMartUser martUser;
}

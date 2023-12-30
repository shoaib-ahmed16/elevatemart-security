package com.elevatemart.security.elevatemartsecurity.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RegisterUser {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String firstName;
    private String lastName;
    private String countryCode;
    private String number;
    private String email;
//    @JsonProperty(access =JsonProperty.Access.WRITE_ONLY)
    private String password;
    private List<String> roles;

}

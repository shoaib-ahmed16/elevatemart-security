package com.elevatemart.security.elevatemartsecurity.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private  T  obj;
    private HttpStatus status;
    private int statusCode;
}

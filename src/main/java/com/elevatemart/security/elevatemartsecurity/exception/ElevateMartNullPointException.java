package com.elevatemart.security.elevatemartsecurity.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ElevateMartNullPointException extends RuntimeException {

   public ElevateMartNullPointException(String message){
       super(message);
   }
}

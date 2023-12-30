package com.elevatemart.security.elevatemartsecurity.services;

import com.elevatemart.security.elevatemartsecurity.domain.RegisterUser;
import com.elevatemart.security.elevatemartsecurity.exception.RegisterUserException;

import java.util.List;


public interface UserService {
    public void registerUser(RegisterUser user);
    public RegisterUser getRegisterUserByEmail(String email) throws RegisterUserException;

    List<RegisterUser> getAllRegisterUserDetails()throws RegisterUserException;

}

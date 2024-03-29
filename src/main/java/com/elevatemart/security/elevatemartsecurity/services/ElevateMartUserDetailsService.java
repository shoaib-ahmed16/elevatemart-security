package com.elevatemart.security.elevatemartsecurity.services;

import com.elevatemart.security.elevatemartsecurity.domain.ElevateMartUser;
import com.elevatemart.security.elevatemartsecurity.exception.ElevateMartUserException;

import java.util.List;

public sealed interface ElevateMartUserDetailsService permits ElevateMartUserDetailsServiceImpl  {

    ElevateMartUser registerUser(ElevateMartUser martUser);

    ElevateMartUser getMartUserDetailsByEmail(String email)throws ElevateMartUserException;

    List<ElevateMartUser> getAllMartUserDetails()throws ElevateMartUserException;

}

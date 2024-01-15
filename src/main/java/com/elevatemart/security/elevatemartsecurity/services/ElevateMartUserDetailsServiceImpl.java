package com.elevatemart.security.elevatemartsecurity.services;

import com.elevatemart.security.elevatemartsecurity.domain.ElevateMartUser;
import com.elevatemart.security.elevatemartsecurity.exception.DatabaseUnknownServerError;
import com.elevatemart.security.elevatemartsecurity.exception.ElevateMartUserException;
import com.elevatemart.security.elevatemartsecurity.repository.ElevateMartUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service(value = "elevateMartUserDetailsService")
public final class ElevateMartUserDetailsServiceImpl implements  ElevateMartUserDetailsService {

    @Autowired
    private ElevateMartUserRepository userRepo;

    @Override
    public ElevateMartUser registerUser(ElevateMartUser martUser) {
        log.info("Initializing the process to save ElevateMart user details to the database. ElevateMart object: {}", martUser);
//        if(Objects.isNull(martUser)){
//            log.info("Request processing halted: The object is empty.");
//            throw new ElevateMartNullPointException("Getting ElevateMart User Object as null");
//        }
        log.info("Saving ElevateMartUser to the database.");
        userRepo.save(martUser);
        log.info("ElevateMartUser successfully saved to the database.");
        return martUser;
    }

    @Override
    public ElevateMartUser getMartUserDetailsByEmail(String email) throws ElevateMartUserException {
        log.info("Initiating retrieval of ElevateMart user details from the database with email: {}", email);
        ElevateMartUser userDetail= userRepo.findByEmail(email).orElseThrow(()->{
            log.error("Failed to retrieve user details. No user found with the email: '{}'", email);
            throw new ElevateMartUserException("No record found of any ElevateMart User with email :"+email);
        } );
        log.info("Returning the ElevateMart user details from the database with email: {}", email);
        return  userDetail;
    }

    @Override
    public List<ElevateMartUser> getAllMartUserDetails() throws ElevateMartUserException,DatabaseUnknownServerError {
        log.info("Initiating retrieval of ElevateMart all user details from the database.");
        List<ElevateMartUser> martUsers = userRepo.findAll();
        log.info("Returning the ElevateMart all user details from the database.");
        return  martUsers;
    }
}

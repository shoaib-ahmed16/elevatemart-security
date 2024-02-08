package com.elevatemart.security.elevatemartsecurity.services;

import com.elevatemart.security.elevatemartsecurity.domain.ROLE;
import com.elevatemart.security.elevatemartsecurity.domain.ElevateMartUser;
import com.elevatemart.security.elevatemartsecurity.exception.ElevateMartUserCredentialNotFoundException;
import com.elevatemart.security.elevatemartsecurity.repository.ElevateMartUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public final class UserDetailsServiceImpl  implements UserDetailsService {
    @Autowired(required = true)
    private ElevateMartUserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Initiating retrieval of ElevateMart user details from the database for authentication object with username: {}", username);
        ElevateMartUser userDetail= userRepo.findByEmail(username).orElseThrow(()->{
            log.error("Failed to retrieve user details for login. No user found with the username: '{}'", username);
            throw new ElevateMartUserCredentialNotFoundException("Invalid Credentials either username or password is wrong!!!");
        } );
        log.info("User Object returned for Authentication Manager method.");
        return new User(userDetail.getEmail(),userDetail.getPassword(),getAuthorities(userDetail.getRole()));
    }
    private List<GrantedAuthority> getAuthorities(String role){
        List<GrantedAuthority> grantedAuthorities= new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(ROLE.PREFIX.getName()+role.toUpperCase()));
        return grantedAuthorities;
    }
}

package com.elevatemart.security.elevatemartsecurity.services.impl;

import com.elevatemart.security.elevatemartsecurity.domain.RegisterUser;
import com.elevatemart.security.elevatemartsecurity.exception.RegisterUserException;
import com.elevatemart.security.elevatemartsecurity.exception.UserCredentialNotFoundException;
import com.elevatemart.security.elevatemartsecurity.repository.UserRepository;
import com.elevatemart.security.elevatemartsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RegisterUser user =null;
        Optional<RegisterUser> admin =userRepository.findByEmail(username);
        if(admin.isPresent()) {
            user=admin.get();
            return new User(user.getEmail(), user.getPassword(), getAuthority(user));
        }
        throw new UserCredentialNotFoundException("Invalid Username or Password");
    }

    private Set<GrantedAuthority> getAuthority(RegisterUser user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
        });
        return authorities;
    }
    @Override
    public void registerUser(RegisterUser user) {
        try {
            userRepository.save(user);
        }catch (Exception exc){

        }
    }

    @Override
    public RegisterUser getRegisterUserByEmail(String email) throws RegisterUserException {
        return userRepository.findByEmail(email).orElseThrow(()-> new RegisterUserException("No User found for the Register Email Id."));
    }

    @Override
    public List<RegisterUser> getAllRegisterUserDetails() throws RegisterUserException {
        List<RegisterUser> registerUsers = userRepository.findAll();
        if(registerUsers.isEmpty()){
            throw new RegisterUserException("No Records found");
        }
        return registerUsers;
    }



}

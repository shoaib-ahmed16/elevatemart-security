package com.elevatemart.security.elevatemartsecurity.services.impl;

import com.elevatemart.security.elevatemartsecurity.domain.RegisterUser;
import com.elevatemart.security.elevatemartsecurity.exception.UserCredentialNotFoundException;
import com.elevatemart.security.elevatemartsecurity.repository.UserRepository;
import com.elevatemart.security.elevatemartsecurity.services.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public void registerUser(RegisterUser user) {
        userRepository.save(user);
    }

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

    private Set<SimpleGrantedAuthority> getAuthority(RegisterUser user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
        });
        return authorities;
    }
}

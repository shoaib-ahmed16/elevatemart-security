package com.elevatemart.security.elevatemartsecurity.controller;

import com.elevatemart.security.elevatemartsecurity.domain.RegisterUser;
import com.elevatemart.security.elevatemartsecurity.exception.UnknownServerError;
import com.elevatemart.security.elevatemartsecurity.services.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("api/v1")
public class Controller {

    @Autowired
    @Resource(name = "userService")
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/signUp")
    public ResponseEntity<String> signUpUser(@RequestBody RegisterUser user){
        if(Objects.nonNull(user)){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.registerUser(user);
            return  new ResponseEntity<>("User is created and successfully saved in Database.", HttpStatus.OK);
        }
        throw new UnknownServerError("Getting Null value of the Object!");
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(Authentication auth){
        System.out.println(auth);
        return  new ResponseEntity<>("User Login Successfully."+auth.getName(),HttpStatus.OK);
    }
    @PostMapping("/logout")
    public  void logout(){

    }
}

package com.elevatemart.security.elevatemartsecurity.controller;

import com.elevatemart.security.elevatemartsecurity.domain.RegisterUser;
import com.elevatemart.security.elevatemartsecurity.exception.UnknownServerError;
import com.elevatemart.security.elevatemartsecurity.services.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping("/signUp")
    public Void signUpUser(@RequestBody RegisterUser user){
        if(Objects.nonNull(user)){
            userService.registerUser(user);
        }
        throw new UnknownServerError("Getting Null value of the Object!");
    }
    @PostMapping("/login")
    public Void loginUser(@RequestBody RegisterUser user){
        if(Objects.nonNull(user)){
            userService.registerUser(user);
        }
        throw new UnknownServerError("Getting Null value of the Object!");
    }
    @PostMapping("/logout")
    public  void logout(){

    }
}

package com.elevatemart.security.elevatemartsecurity.controller;

import com.elevatemart.security.elevatemartsecurity.domain.ElevateMartUser;
import com.elevatemart.security.elevatemartsecurity.services.ElevateMartUserDetailsService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    @Autowired
    @Resource(name = "userService")
    private UserDetailsService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Resource(name = "elevateMartUserDetailsService")
    private ElevateMartUserDetailsService eleMarDetService;

    @GetMapping("/hello")
    @ResponseStatus(HttpStatus.OK)
   public String testHandler(){
        return  "Welcome to Spring Security.";
    }
    @PostMapping("/register")
    public ResponseEntity<ElevateMartUser> saveElevateMartUser(@RequestBody ElevateMartUser martUser){
        martUser.setPassword(passwordEncoder.encode(martUser.getPassword()));
        ElevateMartUser user=eleMarDetService.registerUser(martUser);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @GetMapping("/customer/{email}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ElevateMartUser> getElevateMartUserByEmail(@PathVariable("email") String email){
        return  new ResponseEntity<>(eleMarDetService.getMartUserDetailsByEmail(email),HttpStatus.ACCEPTED);
    }
    @GetMapping("/customers")
    public ResponseEntity<List<ElevateMartUser>> getAllElevateMartUsers(){
        return  new ResponseEntity<List<ElevateMartUser>>(eleMarDetService.getAllMartUserDetails(),HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/signIn")
    public ResponseEntity<String> getLoggedInUserDetailsHandler(Authentication auth){

        System.out.println(auth);
        ElevateMartUser user = eleMarDetService.getMartUserDetailsByEmail(auth.getName());
        return new ResponseEntity<>(user.getFirstName()+user.getLastName()+" Logged In Successfully!!!",HttpStatus.ACCEPTED);
    }
}

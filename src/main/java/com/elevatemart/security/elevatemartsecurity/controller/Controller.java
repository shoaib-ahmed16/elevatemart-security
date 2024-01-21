package com.elevatemart.security.elevatemartsecurity.controller;

import com.elevatemart.security.elevatemartsecurity.domain.ElevateMartUser;
import com.elevatemart.security.elevatemartsecurity.dto.LoginBean;
import com.elevatemart.security.elevatemartsecurity.services.ElevateMartUserDetailsService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class Controller {

//    @Autowired
//    private UserDetailsService userService;

    @Autowired
    private AuthenticationManager authManager;


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
        return  new ResponseEntity<List<ElevateMartUser>>(eleMarDetService.getAllMartUserDetails(),HttpStatus.ACCEPTED);
    }
    // when we are providing the username and password in Authenticate --> basic auth
//    @GetMapping("/signIn")
//    public ResponseEntity<String> getLoggedInUserDetailsHandler(Authentication auth){
//
//        ElevateMartUser user = eleMarDetService.getMartUserDetailsByEmail(auth.getName());
//        return new ResponseEntity<>(user.getFirstName()+user.getLastName()+" Logged In Successfully!!!",HttpStatus.ACCEPTED);
//    }

    @PostMapping("/signIn")
    public ResponseEntity<String> getLoggedInUserDetailsHandler(@RequestBody LoginBean loginBean, HttpServletRequest request){
        try {
            Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginBean.getUsername(),loginBean.getPassword()));
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,sc);
            return  new ResponseEntity<>("Authenitcation successFully!!!",HttpStatus.ACCEPTED);
        }catch (Exception exc){
            return new ResponseEntity<>("Authentication Failed!!!",HttpStatus.BAD_REQUEST);
        }
//        ElevateMartUser user = eleMarDetService.getMartUserDetailsByEmail(auth.getName());
//        return new ResponseEntity<>(user.getFirstName()+user.getLastName()+" Logged In Successfully!!!",HttpStatus.ACCEPTED);
    }
    @PostMapping("/contact")
    public String postDemo1(){
        return  "Not harmful Post operation";
    }
    @PutMapping("/notice")
    public String postDemo2(){
        return  "Not harmful Put operation";
    }

    @PostMapping("/write")
    public String postDemo3(){
        return  "harmful Post operation";
    }
}

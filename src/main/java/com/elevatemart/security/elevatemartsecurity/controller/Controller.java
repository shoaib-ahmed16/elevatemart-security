package com.elevatemart.security.elevatemartsecurity.controller;

import com.elevatemart.security.elevatemartsecurity.config.JwtTokenGeneratorFilter;
import com.elevatemart.security.elevatemartsecurity.domain.ElevateMartUser;
import com.elevatemart.security.elevatemartsecurity.dto.Constants;
import com.elevatemart.security.elevatemartsecurity.dto.LoginBean;
import com.elevatemart.security.elevatemartsecurity.dto.LoginResponse;
import com.elevatemart.security.elevatemartsecurity.services.ElevateMartUserDetailsService;
import com.elevatemart.security.elevatemartsecurity.utils.Response;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class Controller {

    @Autowired
    private UserDetailsService userService;

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
//    when we are providing the username and password in Authenticate --> basic auth
/*      @GetMapping("/signIn")
        public ResponseEntity<String> getLoggedInUserDetailsHandler(Authentication auth){
            ElevateMartUser user = eleMarDetService.getMartUserDetailsByEmail(auth.getName());
            return new ResponseEntity<>(user.getFirstName()+user.getLastName()+" Logged In Successfully!!!",HttpStatus.ACCEPTED);
        }
*/

    @PostMapping("/signIn")
    public Response<LoginResponse> getLoggedInUserDetailsHandler(@RequestBody LoginBean loginBean, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("Initializing user authentication process for sign-in.");
            Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginBean.getUsername(), loginBean.getPassword()));
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
            log.info("Authenticated User have following authorities: {}",auth.getAuthorities());
            String jwtToken=JwtTokenGeneratorFilter.getToke(auth);
            response.setHeader(Constants.JWT_HEADER.getValue(), jwtToken);
            log.info("Authentication token successfully set to the header for the authenticated user: {}", auth.getName());
            LoginResponse loginResponse=new LoginResponse();
            loginResponse.setAuthenticated(auth.getName()+"Authenticated SuccessFully!!!");
            loginResponse.setJwtToken(jwtToken);
            return new Response(loginResponse, HttpStatus.ACCEPTED, HttpStatus.ACCEPTED.value());
        } catch (Exception exc) {
            return new Response("Authentication Failed!!!", HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value());
        }
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

package com.elevatemart.security.elevatemartsecurity.config;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;
    @Resource(name = "userService")
    private UserDetailsService userDetailsService;
    private static final String[] AUTH_WHITELIST = {
            "/api/v1/signup", //entry point api
            "/api/v1/login", // login point api
            "/api/v1/logout"
    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        RequestCache nullRequestCache = new NullRequestCache();
        HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.COOKIES));
        http
                .authorizeHttpRequests(authorize-> authorize
                        .requestMatchers(HttpMethod.POST,AUTH_WHITELIST)
                        .permitAll().anyRequest().authenticated())
                .requestCache((cache)->cache.
                        requestCache(nullRequestCache))
                .csrf(csrf->csrf.disable())
                .sessionManagement(session ->session.
                sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .maximumSessions(2)
                .maxSessionsPreventsLogin(true))
                //.addFilterBefore(new CORSFilter(),JwtAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetails() {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        UserDetails admin = User
                .withUsername("admin")
                .password("password")
                .authorities("ADMIN")
                .build();

        UserDetails user = User
                .withUsername("user")
                .password("1234")
                .authorities("READ")
                .build();
        userDetailsManager.createUser(admin);
        userDetailsManager.createUser(user);
        return userDetailsManager;
    }

//
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
//    }

}

package com.elevatemart.security.elevatemartsecurity.config;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;
    private static final String[] AUTH_WHITELIST = {
            "/api/v1/signup", //entry point api
            "/api/v1/login", // login point api
            "/api/v1/logout"
    };

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        RequestCache nullRequestCache = new NullRequestCache();
        HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.COOKIES));
        http
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize-> authorize
                        .requestMatchers(AUTH_WHITELIST)
                        .permitAll())
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(AUTH_WHITELIST)
                        .permitAll()
                .anyRequest()
                .authenticated())
                .requestCache((cache)->cache.
                        requestCache(nullRequestCache))
                .logout((logout) -> logout.addLogoutHandler(clearSiteData));
        http
                .sessionManagement(session ->session.
                sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .maximumSessions(2)
                .maxSessionsPreventsLogin(true));
        http
                .addFilterBefore(new CORSFilter(),JwtAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    public FilterRegistrationBean<TenantFilter> tenantFilterRegistration(TenantFilter filter) {
//        FilterRegistrationBean<TenantFilter> registration = new FilterRegistrationBean<>(filter);
//        registration.setEnabled(false);
//        return registration;
//    }





}

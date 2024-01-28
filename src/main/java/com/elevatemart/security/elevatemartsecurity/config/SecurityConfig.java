package com.elevatemart.security.elevatemartsecurity.config;

import com.elevatemart.security.elevatemartsecurity.domain.ROLE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtTokenGeneratorFilter jwtTokenGeneratorFilter;

    @Autowired
    private  JwtTokenValidatorFilter jwtTokenValidatorFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        RequestCache nullRequestCache = new NullRequestCache();
        CsrfTokenRequestAttributeHandler requestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.COOKIES));
        http
                .sessionManagement(sessionManger ->sessionManger.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors ->{
                    cors
                        .configurationSource(request->{
                            CorsConfiguration cfg = new CorsConfiguration();
                            cfg.setAllowCredentials(true);
                            cfg.setAllowedOriginPatterns(Collections.singletonList("*"));
                            cfg.setAllowedMethods(Collections.singletonList("*"));
                            cfg.setAllowedHeaders(Collections.singletonList("*"));
                            cfg.setExposedHeaders(Arrays.asList("Authorization"));
                            cfg.setMaxAge(3600L);
                            return cfg;
                        });
                })
                .authorizeHttpRequests(authorize->
                    authorize
                        .requestMatchers(HttpMethod.POST,"/api/v1/register")
                        .permitAll()
                        .requestMatchers( "/api/v1/signIn").permitAll()
                        .requestMatchers("/swagger-ui*/**","v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/customers").hasRole(ROLE.ADMIN.getName())
                        .requestMatchers(HttpMethod.GET,"/api/v1/customer/{email}").hasAnyRole(ROLE.ADMIN.getName(),ROLE.USER.getName())
                        .anyRequest().authenticated())
                .csrf(csrf->
                    csrf
                        .csrfTokenRequestHandler(requestAttributeHandler).ignoringRequestMatchers("/api/v1/contact","/api/v1/notice","/api/v1/register","/api/v1/signIn")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthorisedUserLoggingFilter(),BasicAuthenticationFilter.class)
                .addFilterAfter(jwtTokenGeneratorFilter,BasicAuthenticationFilter.class)
                .addFilterBefore(jwtTokenValidatorFilter,BasicAuthenticationFilter.class)
                .requestCache((cache)->cache.
                        requestCache(nullRequestCache))
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}

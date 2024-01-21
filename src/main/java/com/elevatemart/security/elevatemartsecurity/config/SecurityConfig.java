package com.elevatemart.security.elevatemartsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
//@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    private UnauthorizedEntryPoint unauthorizedEntryPoint;

//    @Autowired
//    private UserDetailsService userDetailsService;
    private static final String[] AUTH_WHITELIST = {
            "/api/v1/register", // login point api
            "/api/v1/signIn"
    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        RequestCache nullRequestCache = new NullRequestCache();
        CsrfTokenRequestAttributeHandler requestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.COOKIES));
        http
                .cors(cors ->{
                    cors.configurationSource(request->{
                                CorsConfiguration cfg = new CorsConfiguration();
                                cfg.setAllowCredentials(true);
                                cfg.setAllowedOriginPatterns(Collections.singletonList("*"));
                                cfg.setAllowedMethods(Collections.singletonList("*"));
                                cfg.setAllowedHeaders(Collections.singletonList("*"));
                                cfg.setExposedHeaders(Arrays.asList("Authorization"));
                                return cfg;
                            });
                })
                .authorizeHttpRequests(authorize-> authorize
                        .requestMatchers(HttpMethod.POST,AUTH_WHITELIST)
                        .permitAll()
                        .requestMatchers("/swagger-ui*/**","v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/customers").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/customer/{email}").hasAuthority("ADMIN2")
                        .anyRequest().authenticated())
                .csrf(csrf->csrf.csrfTokenRequestHandler(requestAttributeHandler).ignoringRequestMatchers("/api/v1/contact","/api/v1/notice","/api/v1/register","/api/v1/signIn")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .requestCache((cache)->cache.
                        requestCache(nullRequestCache))
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
//                .sessionManagement(session ->session.
//                sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .maximumSessions(2)
//                .maxSessionsPreventsLogin(true));
                //.addFilterBefore(new CORSFilter(),JwtAuthenticationFilter.class)
//                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
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

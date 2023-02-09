package com.proyecto.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetail;
    
    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .securityMatcher("/","/usuario/**","/productohome/**")
                .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll())
                .formLogin().loginPage("/usuario/login").permitAll().defaultSuccessUrl("/usuario/acceder");
                

        return http.build();
    }
    
    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        http
                .csrf().disable()
                .securityMatcher("/administrador/**", "/productos/**")
                .authorizeHttpRequests(authorize -> authorize
                .anyRequest().hasRole("ADMIN"))
                .httpBasic();

        return http.build();

    }

    @Bean
    AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetail)
                .passwordEncoder(encoder())
                .and()
                .build();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}

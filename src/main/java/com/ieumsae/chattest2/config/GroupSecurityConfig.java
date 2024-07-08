package com.ieumsae.chattest2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class GroupSecurityConfig {

    @Bean
    public SecurityFilterChain groupFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/chat/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .and()
                .csrf().disable();
        return http.build();
    }
}
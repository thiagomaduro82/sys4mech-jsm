package com.sys4business.sys4mech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity; consider enabling it in production
            .httpBasic(AbstractHttpConfigurer::disable) // Disable basic authentication; adjust as needed
            .formLogin(AbstractHttpConfigurer::disable); // Disable form login; adjust as needed

        return http.build();
    }

}

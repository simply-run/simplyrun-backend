package com.simpllyrun.srcservice.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/api/users/login").authenticated()
                .requestMatchers("/h2-console/**").permitAll()
//                .requestMatchers("/api/manager/**").hasAnyRole("MANAGER", "ADMIN")
//                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable().and() // h2 접속을 위해 설정함 나중에 삭제
                .csrf().disable()
                .cors().disable()
                .formLogin().disable()
                .logout().disable()
                .sessionManagement();

        return http.build();
    }
}

package com.simpllyrun.srcservice.config;

import com.simpllyrun.srcservice.api.auth.service.OAuthService;
import com.simpllyrun.srcservice.handler.OAuth2FailureHandler;
import com.simpllyrun.srcservice.handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuthService OAuthService;
    private final OAuth2SuccessHandler successHandler;
    private final OAuth2FailureHandler failureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
//                .anyRequest().authenticated()
//                .requestMatchers("/api/users/login").authenticated()
                .requestMatchers("/h2-console/**").permitAll()
                .anyRequest().permitAll()
                //.requestMatchers("/api/**").hasRole(User.Role.USER.getKey()) //USER 권한만 접근가능
//                .requestMatchers("/api/manager/**").hasAnyRole("MANAGER", "ADMIN")
//                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .and()
                .headers().frameOptions().disable();

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); //세션 사용 x
        http
                .logout().logoutSuccessUrl("/").and()
                .formLogin().disable()
                .httpBasic().disable(); // id+pw 방식인 httpBasic이 아닌 Token을 들고가는 Bearer 방식 사용하기 위함
        http
                .oauth2Login() //oauth2Login 설정 시작
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .userInfoEndpoint() //oauth2Login 로그인된 유저의 정보를 가져온다.
                .userService(OAuthService); //로그인된 유저의 정보를 customOAuth2UserService에서 처리하겠다

        return http.build();
    }

    //    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //내서버가 응답할 때 json을 자바스크립트에서 처리할 수 있게 할지를 설정하는 것
        config.addAllowedOrigin("*"); //모든 ip에 응답을 허용하겠다
        config.addAllowedHeader("*"); //모든 header에 응답을 허용하겠다
        config.addAllowedMethod("*"); //모든 get, post, put, delete, patch 요청을 허용하겠다
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}

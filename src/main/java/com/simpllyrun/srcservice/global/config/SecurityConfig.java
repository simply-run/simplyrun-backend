package com.simpllyrun.srcservice.global.config;

import com.simpllyrun.srcservice.api.auth.domain.RoleType;
import com.simpllyrun.srcservice.api.auth.jwt.AuthTokenProvider;
import com.simpllyrun.srcservice.api.auth.service.OAuthService;
import com.simpllyrun.srcservice.global.error.RestAuthenticationEntryPoint;
import com.simpllyrun.srcservice.global.filter.TokenAuthenticationFilter;
import com.simpllyrun.srcservice.global.handler.OAuth2FailureHandler;
import com.simpllyrun.srcservice.global.handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuthService oAuthService;
    private final OAuth2SuccessHandler successHandler;
    private final OAuth2FailureHandler failureHandler;
    private final AuthTokenProvider authTokenProvider;

    private static final String[] AUTH_WHITELIST_SWAGGER = {"/v3/api-docs/**", "/swagger-ui/**"};

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
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers(AUTH_WHITELIST_SWAGGER).permitAll()
//                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/api/**").hasAnyAuthority(RoleType.USER.getKey())
//                .requestMatchers("/oauth2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS); //세션 사용 x
        http
                .logout().logoutSuccessUrl("/").and()
                .formLogin().disable()
                .httpBasic().disable() // id+pw 방식인 httpBasic이 아닌 Token을 들고가는 Bearer 방식 사용하기 위함
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint());
        http
                .oauth2Login() //oauth2Login 설정 시작
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")
                .authorizationRequestRepository(authorizationRequestRepository())
                .and()
                .redirectionEndpoint()
                .baseUri("/**/oauth2/code/**")
                .and()
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .userInfoEndpoint() //oauth2Login 로그인된 유저의 정보를 가져온다.
                .userService(oAuthService); //로그인된 유저의 정보를 oAuthService 처리하겠다

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //내서버가 응답할 때 json을 자바스크립트에서 처리할 수 있게 할지를 설정하는 것
        config.addAllowedOrigin("*"); //모든 ip에 응답을 허용하겠다
        config.addAllowedHeader("*"); //모든 header에 응답을 허용하겠다
        config.addAllowedMethod("*"); //모든 get, post, put, delete, patch 요청을 허용하겠다
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public HttpSessionOAuth2AuthorizationRequestRepository authorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(authTokenProvider);
    }
}

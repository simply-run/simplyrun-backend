package com.simpllyrun.srcservice.config;

import com.simpllyrun.srcservice.api.domain.User;
import com.simpllyrun.srcservice.api.repository.UserRepository;
import com.simpllyrun.srcservice.api.service.CustomOAuth2UserService;
import com.simpllyrun.srcservice.handler.OAuth2FailureHandler;
import com.simpllyrun.srcservice.handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CorsFilter corsFilter;
    private final OAuth2SuccessHandler successHandler;
    private final OAuth2FailureHandler failureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //[part1]
                // URL별 권한 과닐 옵션
                .authorizeHttpRequests() //인증/인가 설정 시 HttpServletRequest 이용한다는 의미
                .requestMatchers("/api/users/login").authenticated()
//                .requestMatchers("/api/manager/**").hasAnyRole("MANAGER", "ADMIN")
//                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/**").hasRole(User.Role.USER.getKey()) //USER 권한만 접근가능
                .anyRequest().permitAll() //위의 경로 이외에는 모두 접근 가능
                .and()

                //[part2]
                .formLogin().disable()
                .httpBasic().disable() // id+pw 방식인 httpBasic이 아닌 Token을 들고가는 Bearer 방식 사용하기 위함
                .logout().disable()
                .csrf().disable()
                .cors().disable()
                .addFilter(corsFilter) // 모든 요청 허용하는 필터,,,  @CrossOrigin(인증 없을 때 사용하는 것), 시큐리티 필터에 corsFilter 등록 (인증 있을 때 사용하는 것)
                .headers().frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션 사용 x
                .and()
                .logout().logoutSuccessUrl("/")
                .and()

                // [part3]
                // 소셜 로그인 설정
                .oauth2Login() //oauth2Login 설정 시작
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                    .userInfoEndpoint() //oauth2Login 로그인된 유저의 정보를 가져온다.
                        .userService(customOAuth2UserService); //로그인된 유저의 정보를 customOAuth2UserService에서 처리하겠다

        return http.build();
    }


}

package com.simpllyrun.srcservice.api.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

//@RestController
@Log4j2
public class NaverLoginController {

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String redirectUrl;

    @Value("${spring.security.oauth2.client.provider.naver.token_uri}")
    private String tokenUri;

    @GetMapping("/")
    public String naverLogin(HttpServletResponse response){
        String jwtToken = response.getHeader("Authorization");

        log.info("Authorization ={}", jwtToken);

        return "login";
    }
    @GetMapping("/OAuth/login")
    public void callback(@RequestParam String code,
                           @RequestParam String state, HttpServletResponse response) throws IOException {

        //https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=&client_secret=발급받은clientsecret&code=위에서받은code
        //"https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=aNeAlbn7Lx2R47j_MISP&client_secret=UeNlHaWBPZ&code=au0r5FlCU8WvaoEThg"
        String tokenUrl = tokenUri + "?grant_type=authorization_code&client_id=" + clientId + "&client_secret=" + clientSecret + "&code=" + code;
        log.info("code={}", code);
        log.info("token Url ={}", tokenUrl);
        response.sendRedirect(tokenUrl);
    }


}

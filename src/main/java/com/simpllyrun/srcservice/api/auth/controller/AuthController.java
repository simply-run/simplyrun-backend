package com.simpllyrun.srcservice.api.auth.controller;

import com.simpllyrun.srcservice.api.auth.dto.AuthDto;
import com.simpllyrun.srcservice.api.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthDto authDto) {
        var token = authService.login(authDto.getUserId(), authDto.getPassword());
        if (token == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(token);
        }
    }
}

package com.simpllyrun.srcservice.api.user.controller;

import com.simpllyrun.srcservice.api.user.dto.UserDto;
import com.simpllyrun.srcservice.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "users", description = "사용자 API")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    @Operation(summary = "사용자 조회")
    public ResponseEntity<UserDto> getUser() {
        var user = UserDto.of(userService.getUser());
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }
}

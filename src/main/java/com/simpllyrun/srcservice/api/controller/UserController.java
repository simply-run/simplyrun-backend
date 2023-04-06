package com.simpllyrun.srcservice.api.controller;

import com.simpllyrun.srcservice.api.dto.user.UserLoginDto;
import com.simpllyrun.srcservice.api.dto.user.mapper.UserDtoMapper;
import com.simpllyrun.srcservice.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "users", description = "사용자 API")
public class UserController {

    private final UserService UserService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "사용자 로그인")
    public Boolean userLogin(@RequestBody @Valid UserLoginDto userLogin) {
        return UserService.userLogin(UserDtoMapper.toEntity(userLogin));
    }
}

package com.simpllyrun.srcservice.api.auth.service;

import com.simpllyrun.srcservice.api.auth.dto.AuthDto;

public interface AuthService {
    String login(AuthDto authDto);
}

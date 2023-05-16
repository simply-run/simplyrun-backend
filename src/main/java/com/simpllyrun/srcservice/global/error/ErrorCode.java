package com.simpllyrun.srcservice.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //400
    INPUT_VALUE_INVALID(400, "유효하지 않은 입력입니다."),
    // 403
    FORBIDDEN(403, "접근 권한이 없습니다."),

    // 500
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int status;
    private final String message;
}

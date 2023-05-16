package com.simpllyrun.srcservice.global.error;

import lombok.*;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private int status;
    private String message;

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getStatus(), errorCode.getMessage());
    }
}

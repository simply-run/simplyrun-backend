package com.simpllyrun.srcservice.api.exception;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private int status;
    private String code;
    private String message;
}
